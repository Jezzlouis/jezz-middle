package com.jezz.docker.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.jezz.docker"})
@SpringBootApplication
public class JezzDockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JezzDockerApplication.class, args);
        System.out.println("docker web start ...");
    }

}
