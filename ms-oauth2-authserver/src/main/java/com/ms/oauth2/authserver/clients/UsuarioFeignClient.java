package com.ms.oauth2.authserver.clients;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ms.commons.oauth2.usuarios.models.entity.Usuario;

@FeignClient("servicio-usuarios")
public interface UsuarioFeignClient {
	
	@GetMapping("/usuarios/search/buscar-username")
	Usuario findByUsername(@RequestParam("username") String username);
	
	@GetMapping("/usuarios/{id}")
	Usuario update(@RequestBody Usuario usuario,@PathVariable Long id);
	
	
	
}
