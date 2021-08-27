package com.springcloud.gateway.filters.factory;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class Ejemplo2GatewayFilterFactory extends AbstractGatewayFilterFactory<Ejemplo2GatewayFilterFactory.Config> {
	private final Logger LOGGER = LoggerFactory.getLogger(Ejemplo2GatewayFilterFactory.class);

	public Ejemplo2GatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {		
		
		return new OrderedGatewayFilter((exchange, chain) -> {
			LOGGER.info("========================= EJECUTANDO FILTRO2 FACTORY PRE {} =========================",
					config.mensaje);

			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				Optional.ofNullable(config.cookieValor).ifPresent(cookie -> {
					exchange.getResponse().addCookie(ResponseCookie.from(config.cookieNombre, cookie).build());
				});
				LOGGER.info("========================= EJECUTANDO FILTRO2 FACTORY POST {} =========================",
						config.mensaje);
			}));
		}, 2);
	}

	@Override
	public List<String> shortcutFieldOrder() {
		return List.of("mensaje", "cookieNombre", "cookieValor");
	}

	@Override
	public String name() {
		return "Ejemplo2Filtro";
	}

	public static class Config {
		private String mensaje;
		private String cookieValor;
		private String cookieNombre;

		public String getMensaje() {
			return mensaje;
		}

		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}

		public String getCookieValor() {
			return cookieValor;
		}

		public void setCookieValor(String cookieValor) {
			this.cookieValor = cookieValor;
		}

		public String getCookieNombre() {
			return cookieNombre;
		}

		public void setCookieNombre(String cookieNombre) {
			this.cookieNombre = cookieNombre;
		}

	}

}
