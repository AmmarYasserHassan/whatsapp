package reciever;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import invoker.Invoker;

import java.io.IOException;

public class MqttClient {

    private final String HOST_IP = "localhost";
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

        Consumer consumerChattingApp = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String requestRaw = new String(body);
                JsonObject request = new JsonParser().parse(requestRaw).getAsJsonObject();
                try {
                    invoker.invoke(request.get("commandName").getAsString(), request);
                } catch (Exception e) {

                }
            }
        };

        channel.basicConsume(QUEUE_NAME, true, consumerChattingApp);
    }

    public static void main(String[] args) throws Exception {
        MqttClient client = new MqttClient();
        System.out.println("Chatting app started successfully ");
    }
}
