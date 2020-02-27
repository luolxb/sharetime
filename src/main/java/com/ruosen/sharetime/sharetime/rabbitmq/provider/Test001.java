package com.ruosen.sharetime.sharetime.rabbitmq.provider;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @PackageName: com.ruosen.sharetime.sharetime.rabbitmq.provider
 * @program: sharetime
 * @author: ruosen
 * @create: 2020-02-01 16:51
 **/
public class Test001 {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.44.129");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/ruosen");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        for (int i = 0; i < 5; i++) {
            channel.basicPublish("", "test001", null, "hello rabbitmq".getBytes());
        }
        channel.close();
        connection.close();
    }
}
