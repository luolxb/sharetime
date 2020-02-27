package com.ruosen.sharetime.sharetime.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @PackageName: com.ruosen.sharetime.sharetime.rabbitmq.consumer
 * @program: sharetime
 * @author: ruosen
 * @create: 2020-01-31 16:23
 **/
@Component
public class Receiver {

    @RabbitListener(queues = "share-time-queue")
    public void procss(String msg) {
        System.out.println(msg);
    }
}
