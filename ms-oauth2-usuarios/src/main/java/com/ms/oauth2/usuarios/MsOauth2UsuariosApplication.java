package com.ms.oauth2.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.ms.commons.oauth2.usuarios.models.entity"})
public class MsOauth2UsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsOauth2UsuariosApplication.class, args);
	}

}
