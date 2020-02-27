package com.ruosen.sharetime.sharetime.rabbitmq.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @PackageName: com.ruosen.sharetime.sharetime.rabbitmq.provider
 * @program: sharetime
 * @author: ruosen
 * @create: 2020-01-31 16:18
 **/
@Component
public class InfoSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${amqp.config.exchange}")
    private String exchange;

    @Value("${amqp.config.queue.inforouting.key}")
    private String infoRoutingKey;

    @Value("${amqp.config.queue.errorrouting.key}")
    private String errorRoutingKey;


    public void sendInfo(String msg) {
        amqpTemplate.convertAndSend(exchange, infoRoutingKey, msg);
    }

    public void sendError(String msg) {
        amqpTemplate.convertAndSend(exchange, errorRoutingKey, msg);
    }
}
