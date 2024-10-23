package com.example.journalApp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfig {
//    @Bean
//    public RedisTemplate redisTemplate(RedisConnectionFactory factory){
//        log.error(factory.getConnection().toString());
//
//        RedisTemplate redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(factory);
//
//
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        return redisTemplate;
//    }


}
