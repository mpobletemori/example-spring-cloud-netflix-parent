package com.app.zuul.oauth2;

import java.util.Arrays;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@RefreshScope
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Value("${config.security.oauth2.jwt.key}")
	private String jwtKey;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/api/security/oauth/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/productos/listar", 
						                     "/api/items/listar",
						                     "/api/usuarios/usuarios").permitAll()
				.antMatchers(HttpMethod.GET, "/api/productos/ver/{id}",
						                     "/api/items/ver/{id}/cantidad/{cantidad}",
						                     "/api/usuarios/usuarios/{id}").hasAnyRole("ADMIN", "USER")
				// opcion 1
//		.antMatchers(HttpMethod.POST,"/api/productos/crear"
//                ,"/api/items/crear"
//                ,"/api/usuarios/usuarios").hasRole("ADMIN")
//		.antMatchers(HttpMethod.PUT,"/api/productos/editar/{id}"
//                ,"/api/items/editar/{id}"
//                ,"/api/usuarios/usuarios/{id}").hasRole("ADMIN")
//		.antMatchers(HttpMethod.DELETE,"/api/productos/eliminar/{id}"
//                ,"/api/items/eliminar/{id}"
//                ,"/api/usuarios/usuarios/{id}").hasRole("ADMIN")
				// opcion 2 version simplifiada de opcion 1
				.antMatchers("/api/productos/**", "/api/items/**", "/api/usuarios/**").hasRole("ADMIN")
				// en caso de otras rutas no configuradas previamente debe ser autenticadas
				.anyRequest().authenticated()
				.and()
				.cors().configurationSource(corsConfigurationSource());

	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(Arrays.asList("*"));
		corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(
				new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey(Base64.getEncoder().encodeToString(this.jwtKey.getBytes()));
		return jwtAccessTokenConverter;
	}

}
