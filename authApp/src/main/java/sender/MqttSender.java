package sender;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class MqttSender {
    private final static String QUEUE_NAME = "response";
    private ConnectionFactory factory;
    private Channel channel;
    private Connection connection;

    public MqttSender() throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        factory = new ConnectionFactory();
        factory.setUri("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    }

    public void send(JSONObject res) throws IOException {
        channel.basicPublish("", QUEUE_NAME, null, res.toString().getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + res.toString() + "'");
    }

    public void close() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }


}
