package com.springingdream.adviser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AdviserApplication {
	public static void main(String[] args) {
		SpringApplication.run(AdviserApplication.class, args);
	}
}
