package com.ms.oauth2.authserver.service;

import com.ms.commons.oauth2.usuarios.models.entity.Usuario;

public interface UsuarioService {
	Usuario findByUsername(String username);
}
