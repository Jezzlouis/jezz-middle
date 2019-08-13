package com.jezz.springconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.jezz.springconsumer"
})
public class SpringConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringConsumerApplication.class, args);
    }

}
