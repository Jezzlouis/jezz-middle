package com.jezz.shardingjdbc.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.jezz.mapper"})
@ComponentScan(basePackages = {"com.jezz.shardingjdbc"})
public class ShardingJdbcFirstApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShardingJdbcFirstApplication.class, args);
	}
}
