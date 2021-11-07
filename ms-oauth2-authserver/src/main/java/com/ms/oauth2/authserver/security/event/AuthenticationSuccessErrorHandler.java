package com.ms.oauth2.authserver.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.ms.oauth2.authserver.service.UsuarioService;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	private static Logger LOGGER = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
	
	@Autowired
	private UsuarioService usuarioService;
	

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		if(authentication.getDetails() instanceof WebAuthenticationDetails) {
			return;
		}
		UserDetails user = (UserDetails) authentication.getPrincipal();
		String mensaje = "Success Login: " + user.getUsername();
		System.out.println(mensaje);
		LOGGER.info(mensaje);
		this.usuarioService.reiniciarContadorIntentoFallido(authentication);

	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String mensaje = "Error en login:" + exception.getMessage();
		System.out.println(mensaje);
		LOGGER.error(mensaje, exception);
		this.usuarioService.registarIntentosFallido(authentication);
	}

	
		

}
