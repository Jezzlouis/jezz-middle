package com.jezz.dubbo.provider.main;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.jezz.dubbo.provider.main",
        "com.jezz.dubbo.provider.service",
        "com.jezz.dubbo.service.api"
})
@EnableDubboConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("dubbo提供者启动 ...");
    }

}
