package com.pancm.mq.rabbitmq.one2one;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
public class ClientReceive1 {
    public static final String queue_name="my_queue";
    public static final boolean autoAck=false;
    public static final boolean durable=true;
    public static void main(String[] args)
    throws java.io.IOException,java.lang.InterruptedException, TimeoutException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException{
        ConnectionFactory factory=new ConnectionFactory();
//        factory.setHost("localhost");
//        factory.setVirtualHost("my_mq");
//        factory.setUsername("zhxia");
//        factory.setPassword("123456");
        factory.setUri("amqp://guest:guest@172.26.129.3:5672");//获取url
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();
        channel.queueDeclare(queue_name, durable, false, false, null);
        System.out.println("Wait for message");
        channel.basicQos(1); //消息分发处理
        QueueingConsumer consumer=new QueueingConsumer(channel);
        channel.basicConsume(queue_name, autoAck, consumer);
        while(true){
            Thread.sleep(500);
            QueueingConsumer.Delivery deliver=consumer.nextDelivery();
            String message=new String(deliver.getBody());
            System.out.println("Message received:"+message);
            channel.basicAck(deliver.getEnvelope().getDeliveryTag(), false);
        }
    }
}