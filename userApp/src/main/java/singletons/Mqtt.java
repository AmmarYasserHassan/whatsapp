package singletons;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.*;
import config.ApplicationProperties;
import invoker.Invoker;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Mqtt {
    private static volatile Mqtt mqtt;
    private final String HOST_IP = ApplicationProperties.getRabbitMqHost();
    private final String QUEUE_NAME = "userApp";

    private ConnectionFactory factory;
    private Channel channel;
    private Connection connection;
    private Invoker invoker;
    private boolean freeze = false;

    private Mqtt() throws Exception {
        invoker = new Invoker();

        factory = new ConnectionFactory();
        factory.setHost(HOST_IP);
        connection = factory.newConnection();
        channel = connection.createChannel();
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-max-length", 1000);
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);
        Consumer consumerChattingApp = getConsumer();

        channel.basicConsume(QUEUE_NAME, false, consumerChattingApp);
    }

    public Consumer getConsumer() {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {

                AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(properties.getCorrelationId())
                        .build();

                String requestRaw = new String(body);
                JsonObject request = new JsonParser().parse(requestRaw).getAsJsonObject();
                try {
                    if (!Mqtt.getInstance().freeze) {
                        String result = invoker.invoke(request.get("command").getAsString(), request);
                        channel.basicPublish("", properties.getReplyTo(), replyProps, result.getBytes());
                        channel.basicAck(envelope.getDeliveryTag(), false);

                    } else {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("state", "app is now freezed");
                        channel.basicPublish("", properties.getReplyTo(), replyProps, jsonObject.toString().getBytes());
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                    synchronized (this) {
                        this.notify();
                    }
                } catch (Exception e) {
                    JSONObject error = new JSONObject();
                    error.put("message", e);
                    System.out.println(e);
                    channel.basicPublish("", properties.getReplyTo(), replyProps, error.toString().getBytes());
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
    }

    public static Mqtt getInstance() throws Exception {
        if (mqtt != null) return mqtt;

        synchronized (Mqtt.class) {

            if (mqtt == null) {

                mqtt = new Mqtt();
            }
        }

        return mqtt;
    }

    public void stop() throws IOException, TimeoutException {
        this.channel.close();
    }

    public void resume() throws IOException {
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);
        Consumer consumerChattingApp = getConsumer();

        channel.basicConsume(QUEUE_NAME, false, consumerChattingApp);
    }

    public void setFreeze(boolean flag) {
        this.freeze = flag;
    }
}
