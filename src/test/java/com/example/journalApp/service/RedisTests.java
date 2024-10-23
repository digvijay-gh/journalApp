package com.example.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest
public class RedisTests {
    private static final Logger log = LoggerFactory.getLogger(RedisTests.class);
    @Autowired
    private RedisTemplate redisTemplate;
    @Disabled
    @Test
    void sendMailTest() {
        redisTemplate.opsForValue().set("email", "digvijaydsy2@gmail.com");
        Object email = redisTemplate.opsForValue().get("amount");
        log.info(email.toString());

        int n=1;
    }
}
