package com.swa.DataInputService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableKafka
@EnableScheduling
@EnableDiscoveryClient
public class DataInputServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataInputServiceApplication.class, args);
	}

}
