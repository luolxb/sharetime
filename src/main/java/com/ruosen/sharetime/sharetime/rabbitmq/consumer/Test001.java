package com.ruosen.sharetime.sharetime.rabbitmq.consumer;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @PackageName: com.ruosen.sharetime.sharetime.rabbitmq.consumer
 * @program: sharetime
 * @author: ruosen
 * @create: 2020-01-31 16:03
 **/
public class Test001 {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.44.129");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/ruosen");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("test001", true, false, false, null);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel);
        channel.basicConsume("test001", true, defaultConsumer);


        channel.close();
        connection.close();
    }
}
