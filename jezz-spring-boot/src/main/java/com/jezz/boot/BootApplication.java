package com.jezz.boot;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication sp = new SpringApplication(BootApplication.class);
        sp.setBannerMode(Banner.Mode.CONSOLE);
        sp.run(args);
    }

}
