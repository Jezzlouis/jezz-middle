package com.jezz.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;


// redis ceshi
@ComponentScan(basePackages = {"com.jezz.config"})
@SpringBootApplication
public class RedisApplication {

    public RedisApplication(RedisTemplate<String, String> redisTemplate) {
        redisTemplate.opsForValue().set("hiiii", "world");
        String ans = redisTemplate.opsForValue().get("hiiii");
        Assert.isTrue("world".equals(ans));
        System.out.println(ans);
    }

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
        System.out.println("redis test...");
    }
}


