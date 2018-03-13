package reciever;

import com.rabbitmq.client.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import invoker.Invoker;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class MqttClient {

    private final String HOST_IP = "localhost";
    private final String QUEUE_NAME = "chattingApp";

    private ConnectionFactory factory;
    private Channel channel;
    private Connection connection;
    private Invoker invoker;

    public MqttClient() throws IOException, TimeoutException {
        invoker = new Invoker();

        factory = new ConnectionFactory();
        factory.setHost(HOST_IP);
        connection = factory.newConnection();

        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        Consumer consumerChattingApp = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String requestRaw = new String(body);
                JsonObject request = new JsonParser().parse(requestRaw).getAsJsonObject();
                HashMap<String, String> args = new HashMap<String, String>();
                try {
                    invoker.invoke(request.get("commandName").getAsString(), args);
                } catch (Exception e) {

                }
            }
        };

        channel.basicConsume(QUEUE_NAME, true, consumerChattingApp);
    }
}
