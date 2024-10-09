package com.example.fanmon_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FanmonBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FanmonBeApplication.class, args);
	}

}
