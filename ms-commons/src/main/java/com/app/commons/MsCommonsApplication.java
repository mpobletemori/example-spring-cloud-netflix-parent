package com.app.commons;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class MsCommonsApplication {
//comentar por que es un jar como dependencia no un ejecutable
//	public static void main(String[] args) {
//		SpringApplication.run(MsCommonsApplication.class, args);
//	}

}
