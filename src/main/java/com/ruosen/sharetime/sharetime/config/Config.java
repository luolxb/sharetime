package com.ruosen.sharetime.sharetime.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @PackageName: com.ruosen.sharetime.sharetime.config
 * @program: sharetime
 * @author: ruosen
 * @create: 2020-01-28 13:34
 **/
@Component
public class Config {

    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public void redisSerializer() {
        RedisSerializer redisSerializer = new StringRedisSerializer();
        RedisSerializer jsonRedisSerializer = new FastJsonRedisSerializer(JSON.class);
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(jsonRedisSerializer);

    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
