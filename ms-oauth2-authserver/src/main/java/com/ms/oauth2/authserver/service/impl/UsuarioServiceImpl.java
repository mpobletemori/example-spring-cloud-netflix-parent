/**
 * 
 */
package com.ms.oauth2.authserver.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ms.commons.oauth2.usuarios.models.entity.Usuario;
import com.ms.oauth2.authserver.clients.UsuarioFeignClient;
import com.ms.oauth2.authserver.service.UsuarioService;

import feign.FeignException;

/**
 * @author vector
 *
 */
@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService {

	private static Logger LOGGER = LoggerFactory.getLogger(UsuarioServiceImpl.class);
	private UsuarioFeignClient usuarioFeignClient;

	/**
	 * @param usuarioFeignClient
	 */
	public UsuarioServiceImpl(UsuarioFeignClient usuarioFeignClient) {
		this.usuarioFeignClient = usuarioFeignClient;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = this.usuarioFeignClient.findByUsername(username);
		if (Objects.isNull(usuario)) {
			final String msg = "Error en login,no existe el usuario '" + username + "'en el sistema";
			LOGGER.error(msg);
			throw new UsernameNotFoundException(msg);
		}
		return toUserDetails(username, usuario);
	}

	private UserDetails toUserDetails(String username, Usuario usuario) {
		List<GrantedAuthority> authorities = usuario.getRoles().stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
				.peek(authority -> LOGGER.info("Role: " + authority.getAuthority())).collect(Collectors.toList());
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		LOGGER.info("usuario " + username + " autenticado");
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
	}

	@Override
	public Usuario findByUsername(String username) {
		return this.usuarioFeignClient.findByUsername(username);
	}

	@Override
	public Usuario update(Usuario usuario, Long id) {
		return this.usuarioFeignClient.update(usuario, id);
	}

	@Override
	public void registarIntentosFallido(Authentication authentication) {
		String userName = authentication.getName();
		try {
			Usuario usuario = this.findByUsername(userName);
			if (Objects.isNull(usuario.getIntentos())) {
				usuario.setIntentos(0);
			}
			LOGGER.info("Intentos actual es de :" + usuario.getIntentos());
			usuario.setIntentos(usuario.getIntentos() + 1);
			LOGGER.info("Intentos despues es de :" + usuario.getIntentos());
			if (usuario.getIntentos() >= 3) {
				LOGGER.error(String.format("El usuario %s deshabilitado por maximo de intentos", userName));
				usuario.setEnabled(false);
			}
			this.update(usuario, usuario.getId());

		} catch (FeignException e) {
			LOGGER.error(String.format("El usuario %s no existe en el sistema", userName), e);
		}
	}

	@Override
	public void reiniciarContadorIntentoFallido(Authentication authentication) {
		String userName = authentication.getName();
		try {
			Usuario usuario = this.findByUsername(userName);
			if (!Objects.isNull(usuario.getIntentos()) && usuario.getIntentos() > 0) {
				usuario.setIntentos(0);
				this.update(usuario, usuario.getId());
			}
		} catch (FeignException e) {
			LOGGER.error(String.format("El usuario %s no existe en el sistema", userName), e);
		}

	}

}
