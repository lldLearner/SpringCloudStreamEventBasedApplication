package com.example.processor.demo;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CashCardSpringCloudStreamProcessorApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(CashCardSpringCloudStreamProcessorApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8081")); // Different port
		app.run(args);
	}

}
