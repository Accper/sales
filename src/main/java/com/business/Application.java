package com.business;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.business.mapper")
@EnableAsync
public class Application {

	public static void main(String[] args) {
		System.out.println("sales");
		SpringApplication.run(Application.class, args);
	}
}
