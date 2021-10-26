/**
 * 
 */
package com.ms.oauth2.authserver.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.ms.commons.oauth2.usuarios.models.entity.Usuario;
import com.ms.oauth2.authserver.service.UsuarioService;

/**
 * @author vector
 *
 */
@Component
public class InfoAdicionalToken implements TokenEnhancer {
    
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Usuario user = this.usuarioService.findByUsername(authentication.getName());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(toAddInfo(user)); 
		return accessToken;
	}

	private Map<String, Object> toAddInfo(Usuario user) {
		Map<String,Object> info = new HashMap<>();
		info.put("nombre", user.getNombre());
		info.put("apellido", user.getApellido());
		info.put("correo", user.getEmail());
		return info;
	}

}
