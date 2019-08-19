package com.jezz.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SessionApplication.class, args);
    }

}
