package com.larkersos.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class EurekaHi2Application {

	public static void main(String[] args) {
		SpringApplication.run(EurekaHi2Application.class, args);
	}
}
