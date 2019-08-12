package com.jezz.dubbo.consumer.main;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.jezz.dubbo.consumer.main",
        "com.jezz.dubbo.consumer.controller",
        "com.jezz.dubbo.consumer.service",
        "com.jezz.dubbo.consumer.filter",
        "com.jezz.dubbo.consumer.intercepter",
        "com.jezz.dubbo.service.api"
})
@EnableDubboConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("META-INF.dubbo-消费者启动 ...");
    }

}
