package com.ruosen.sharetime.sharetime.rabbitmq.provider;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @PackageName: com.ruosen.sharetime.sharetime.rabbitmq.provider
 * @program: sharetime
 * @author: ruosen
 * @create: 2020-01-31 16:02
 **/
@Configuration
public class test {

    /**
     * 创建队列
     *
     * @return
     */
    @Bean
    public Queue createQueue() {
        return new Queue("share-time-queue");
    }
}
