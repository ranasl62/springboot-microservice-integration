package com.DSGS;

import com.DSGS.service.GitService;
import com.DSGS.service.MvnService;
import com.DSGS.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableFeignClients
public class DSGSApplication implements CommandLineRunner {
    @Autowired
	private SupplierService supplierService;

	@Autowired
	private MvnService mvnService;


	public static void main(String[] args) {
		SpringApplication.run(DSGSApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
