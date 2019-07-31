package com.jezz.dymdatasource.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = {
        "com.jezz.dymdatasource.config",
        "com.jezz.dymdatasource.service"
})
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@MapperScan(basePackages = "com.jezz.dymdatasource.mapper")
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
public class DymdatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DymdatasourceApplication.class, args);
    }

}
