package com.ruosen.sharetime.sharetime.rabbitmq.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @PackageName: com.ruosen.sharetime.sharetime.rabbitmq.provider
 * @program: sharetime
 * @author: ruosen
 * @create: 2020-01-31 16:18
 **/
@Component
public class Sender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String msg) {
        amqpTemplate.convertAndSend("share-time-queue", msg);
    }
}
