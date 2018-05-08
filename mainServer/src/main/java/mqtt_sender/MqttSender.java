package mqtt_sender;


import com.rabbitmq.client.*;
import config.ApplicationProperties;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MqttSender {

    private Connection connection;
    private Channel channel;
    private ConnectionFactory factory = new ConnectionFactory();

    private String requestQueueName = "";
//    private String replyQueueName;

    public MqttSender(String queueName) throws IOException, InterruptedException, TimeoutException {
        this.requestQueueName = queueName;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ApplicationProperties.getRabbitMqHost());
        connection = factory.newConnection();
        channel = connection.createChannel();

    }

    public String send(String msg) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();
        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", requestQueueName, props, msg.getBytes("UTF-8"));

        final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);

        channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if (properties.getCorrelationId().equals(corrId)) {
                    response.offer(new String(body, "UTF-8"));
                }
            }
        });
        String ret = response.poll(500, TimeUnit.MILLISECONDS);
        if (ret != null)
            return ret;
        else
            return new String(ApplicationProperties.getTimeout().getBytes(), "UTF-8");
    }

    public void close() throws IOException {
        connection.close();
    }
}