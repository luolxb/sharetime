package com.ruosen.sharetime.sharetime.rabbitmq.consumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @PackageName: com.ruosen.sharetime.sharetime.rabbitmq.consumer
 * @program: sharetime
 * @author: ruosen
 * @create: 2020-01-31 18:10
 **/
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${amqp.config.queue.info}", autoDelete = "true"),
                exchange = @Exchange(value = "${amqp.config.exchange}", type = ExchangeTypes.DIRECT),
                key = "${amqp.config.queue.inforouting.key}"))
@Component
public class InfoReceiver {

    @RabbitHandler
    public void procss(String msg) {
        System.out.println("info:----" + msg);
    }
}
