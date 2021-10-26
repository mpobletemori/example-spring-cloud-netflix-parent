package com.ms.oauth2.authserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;
    
	//inicio configuracion de clients
	//Configuracion de niveles de permisos para las url de autenticacion y autorizacion (validacion de token)
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
		.tokenKeyAccess("permitAll()")//permitir acceso a la url de generacion de token /oauth/token
		.checkTokenAccess("isAuthenticated()");//debe ser autenticado la validacion del token
	}
    
	//configuracion de credenciales de clients
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
		 .withClient("frontendapp")
		 .secret(passwordEncoder.encode("12345"))
		 .scopes("read","write")
		 .authorizedGrantTypes("password","refresh_token")
		 .accessTokenValiditySeconds(3600)
		 .refreshTokenValiditySeconds(3600)
		 .and()
		 .withClient("androidapp")
		 .secret(passwordEncoder.encode("12345"))
		 .scopes("read","write")
		 .authorizedGrantTypes("password","refresh_token")
		 .accessTokenValiditySeconds(3600)
		 .refreshTokenValiditySeconds(3600);
	}
	//fin configuracion de clients
    
	
	//inicio configuracion usuarios owners
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(this.authenticationManager)
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter());
	}
    
	@Bean
	public TokenStore tokenStore() {		
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey("algun_codigo_secreto_aeiou");
		return jwtAccessTokenConverter;
	}
	//fin configuracion usuarios owners

}
