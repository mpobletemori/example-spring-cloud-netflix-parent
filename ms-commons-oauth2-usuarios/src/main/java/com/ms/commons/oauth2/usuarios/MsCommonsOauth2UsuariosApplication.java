package com.ms.commons.oauth2.usuarios;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class MsCommonsOauth2UsuariosApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(MsCommonsOauth2UsuariosApplication.class, args);
//	}

}
