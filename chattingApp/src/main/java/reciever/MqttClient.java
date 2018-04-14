package reciever;

import com.rabbitmq.client.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import config.ApplicationProperties;
import invoker.Invoker;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MqttClient {

    static private Logger logger = LoggerFactory.getLogger(MqttClient.class);


    private final String HOST_IP = ApplicationProperties.getRabbitMqHost();
    private final String QUEUE_NAME = "chattingApp";

    private ConnectionFactory factory;
    private Channel channel;
    private Connection connection;
    private Invoker invoker;

    public MqttClient() throws Exception {
        invoker = new Invoker();

        factory = new ConnectionFactory();
        factory.setHost(HOST_IP);
        connection = factory.newConnection();

        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);
        Consumer consumerChattingApp = new DefaultConsumer(channel) {
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
                    System.out.println(request.get("command").getAsString());
                    String result = invoker.invoke(request.get("command").getAsString(), request);
                    channel.basicPublish("", properties.getReplyTo(), replyProps, result.getBytes());
                    channel.basicAck(envelope.getDeliveryTag(), false);

                    synchronized (this) {
                        this.notify();
                    }
                } catch (Exception e) {
                    JSONObject error = new JSONObject();
                    error.put("message", e);
                    channel.basicPublish("", properties.getReplyTo(), replyProps, error.toString().getBytes());
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        channel.basicConsume(QUEUE_NAME, false, consumerChattingApp);
    }

    public static void main(String[] args) throws Exception {
        Thread.sleep(20000);
        try {
            MqttClient client = new MqttClient();
            logger.info("Connected to rabbitmq on queue " + client.QUEUE_NAME);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        logger.info("Chatting app started successfully ");
    }
}
