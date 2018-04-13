package reciever;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.*;
import database.MongoDBConnection;
import database.PostgreSqlDBConnection;
import invoker.Invoker;
import org.json.JSONObject;

import java.io.IOException;

public class MqttClient {

    private final String HOST_IP = System.getenv("RABBITMQ_HOST");
    private final String QUEUE_NAME = "authApp";

    private ConnectionFactory factory;
    private Channel channel;
    private Connection connection;
    private Invoker invoker;

    public MqttClient() throws Exception {
        invoker = new Invoker();
        System.out.println(HOST_IP);
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
        Thread.sleep(60000);
        try {
            MqttClient client = new MqttClient();
            System.out.println("Auth app started successfully ");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            PostgreSqlDBConnection connection = new PostgreSqlDBConnection();
            connection.connect();
            System.out.println("CONNECTED -----> POSTGRES");
        } catch (Exception e) {
            System.out.println("ERROR PSOTGRESS");
        }

        try {
            MongoDBConnection mongoDBConnection = new MongoDBConnection();
            mongoDBConnection.connect();
            System.out.println("CONNECTED -----> MONGO");
        } catch (Exception e) {
            System.out.println("ERROR MONGO");
        }

    }
}
