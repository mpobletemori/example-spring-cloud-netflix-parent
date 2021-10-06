package com.app.item;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
//import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

//@EnableCircuitBreaker
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class SpringbootServicioItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioItemApplication.class, args);
	}
	
	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer(){
		return factory -> factory.configureDefault(id->{
			return new Resilience4JConfigBuilder(id)
					.circuitBreakerConfig(CircuitBreakerConfig.custom()
							.slidingWindowSize(10)//cantidad total de request a evaluar
							.failureRateThreshold(50)//radio en porcentaje de request fallados
							.waitDurationInOpenState(Duration.ofSeconds(10L))//tiempo en segundos de duracion de estado abierto del circuito
							.permittedNumberOfCallsInHalfOpenState(5)//permitido numero de request en estado semiabierto
							//begin manejo llamadas lentas
							.slowCallRateThreshold(50)//para el caso de llamada lentas umbral de porcentaje de llamadas lentas
							.slowCallDurationThreshold(Duration.ofSeconds(2L))//cantidad en segundos para determinar si request es lenta
							//end manejo llamadas lentas
							.build())
					//begin manejo de timeout de request
					//.timeLimiterConfig(TimeLimiterConfig.ofDefaults())//tiempo limite por defecto en caso de timeout de request
					.timeLimiterConfig(TimeLimiterConfig.custom()
											.timeoutDuration(Duration.ofSeconds(3L))
											.build())
					//end manejo de timeout de request
					.build();
		});
		
	}

}
