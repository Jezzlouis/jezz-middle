package com.jezz.order.consumer.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan(basePackages = {"com.jezz.order.consumer.mapper"})
@ComponentScan(basePackages = {
        "com.jezz.order.consumer",
        "com.jezz.redis"})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("seconds-kill-order-consumer 启动...");
    }

}
