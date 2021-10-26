package com.ms.oauth2.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class MsOauth2AuthserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsOauth2AuthserverApplication.class, args);
	}

}
