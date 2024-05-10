package com.mca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class McaApplication {

	public static void main(String[] args) {
		SpringApplication.run(McaApplication.class, args);
	}

}