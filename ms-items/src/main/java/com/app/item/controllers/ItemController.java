package com.app.item.controllers;

import java.util.List;

import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.item.models.Item;
import com.app.item.models.Producto;
import com.app.item.models.services.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ItemController {
	private final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
	private ItemService itemService;
	private CircuitBreakerFactory cbFactory;

	public ItemController(ItemService itemService, CircuitBreakerFactory cbFactory) {
		this.itemService = itemService;
		this.cbFactory = cbFactory;
	}

	@GetMapping("/listar")
	public List<Item> listar(@RequestParam(name = "nombre", required = false) String nombre,
			@RequestHeader(name = "token-request", required = false) String token) {
		System.out.println("nombre request=" + nombre);
		System.out.println("token-request=" + token);
		return this.itemService.findAll();
	}

	// @HystrixCommand(fallbackMethod = "metodoAlternativo")
	@GetMapping("/ver/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		return this.cbFactory.create("items").run(()->this.itemService.findById(id, cantidad),e->metodoAlternativo(id, cantidad,e));	
	}
	
	@CircuitBreaker(name = "items")
	@GetMapping("/ver2/{id}/cantidad/{cantidad}")
	public Item detalle2(@PathVariable Long id, @PathVariable Integer cantidad) {
		return this.itemService.findById(id, cantidad);
	}

	public Item metodoAlternativo(Long id, Integer cantidad,Throwable e) {
		LOGGER.info(e.getMessage());
		return Item.builder().cantidad(cantidad)
				.producto(Producto.builder().id(id).nombre("prodError").precio(500.00).build()).build();
	}

}
