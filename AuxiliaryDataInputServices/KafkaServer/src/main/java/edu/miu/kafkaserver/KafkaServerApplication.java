package edu.miu.kafkaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class KafkaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaServerApplication.class, args);
	}

}
