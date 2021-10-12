package com.app.item.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.item.models.Item;
import com.app.item.models.Producto;
import com.app.item.models.services.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RefreshScope
@RestController
public class ItemController {
	private final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
	private ItemService itemService;
	private CircuitBreakerFactory cbFactory;
	private String texto;
	@Autowired
	private Environment env;

	public ItemController(ItemService itemService, CircuitBreakerFactory cbFactory,@Value("${configuracion.text}") String texto) {
		this.itemService = itemService;
		this.cbFactory = cbFactory;
		this.texto = texto;
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
		return this.cbFactory.create("items").run(() -> this.itemService.findById(id, cantidad),
				e -> metodoAlternativo(id, cantidad, e));
	}

	@CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo")
	@GetMapping("/ver2/{id}/cantidad/{cantidad}")
	public Item detalle2(@PathVariable Long id, @PathVariable Integer cantidad) {
		return this.itemService.findById(id, cantidad);
	}
	
	@TimeLimiter(name = "items",fallbackMethod = "metodoAlternativo2")
	@GetMapping("/ver3/{id}/cantidad/{cantidad}")
	public CompletableFuture<Item> detalle3(@PathVariable Long id, @PathVariable Integer cantidad) {
		return CompletableFuture.supplyAsync(() -> this.itemService.findById(id, cantidad));
	}
	
	@CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo2")
	@TimeLimiter(name = "items")
	@GetMapping("/ver4/{id}/cantidad/{cantidad}")
	public CompletableFuture<Item> detalle4(@PathVariable Long id, @PathVariable Integer cantidad) {
		return CompletableFuture.supplyAsync(() -> this.itemService.findById(id, cantidad));
	}

	public Item metodoAlternativo(Long id, Integer cantidad, Throwable e) {
		LOGGER.info(e.getMessage());
		return Item.builder().cantidad(cantidad)
				.producto(Producto.builder().id(id).nombre("prodError").precio(500.00).build()).build();
	}

	public CompletableFuture<Item> metodoAlternativo2(Long id, Integer cantidad, Throwable e) {
		LOGGER.info(e.getMessage());
		return CompletableFuture.supplyAsync(() -> {
			return Item.builder().cantidad(cantidad)
					.producto(Producto.builder().id(id).nombre("prodError").precio(500.00).build()).build();
		});
	}
	
	@GetMapping("/obtener-config")
	public ResponseEntity<?> obtenerConfig(){
		LOGGER.info("texto {0}",this.texto);
		Map<String,String> resp = new HashMap<>();
		resp.put("texto", this.texto);
		
		if(this.env.getActiveProfiles().length>0 && this.env.getActiveProfiles()[0].equals("dev")) {
			resp.put("configuracion.autor.nombre", this.env.getProperty("configuracion.autor.nombre","none"));
			resp.put("configuracion.autor.email", this.env.getProperty("configuracion.autor.email","none"));
		}
		return ResponseEntity.ok(resp);
	}
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {
		return this.itemService.save(producto);
	}
	
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto update(@RequestBody Producto producto, @PathVariable Long id) {
		return this.itemService.update(producto, id);
	}
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		this.itemService.delete(id);
	}
	
}
