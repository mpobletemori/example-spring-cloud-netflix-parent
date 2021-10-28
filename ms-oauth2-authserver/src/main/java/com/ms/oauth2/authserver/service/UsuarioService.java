package com.ms.oauth2.authserver.service;

import org.springframework.security.core.Authentication;

import com.ms.commons.oauth2.usuarios.models.entity.Usuario;

public interface UsuarioService {
	Usuario findByUsername(String username);

	Usuario update(Usuario usuario, Long id);
	
	void registarIntentosFallido(Authentication authentication);
	void reiniciarContadorIntentoFallido(Authentication authentication);
}
