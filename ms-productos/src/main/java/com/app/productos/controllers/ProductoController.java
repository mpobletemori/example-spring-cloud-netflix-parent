package com.app.productos.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.commons.models.entity.Producto;
import com.app.productos.models.service.IProductoService;

@RestController
public class ProductoController {

	private IProductoService iproductoService;

	public ProductoController(IProductoService iproductoService) {
		this.iproductoService = iproductoService;
	}

	@GetMapping("/listar")
	public List<Producto> listar() {
		return this.iproductoService.findAll();
	}

	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id) throws InterruptedException {

		// begin pruebas con resilience4j
//		if(id.equals(10L)) {
//			throw new IllegalStateException("Producto no encontrado!");			
//		}
//		if(id.equals(7L)) {
//			TimeUnit.SECONDS.sleep(5L);
//		}
		// end pruebas con resilience4j

		// begin prueba para tolerancia fallos hystrix
//		boolean ok = false;
//		if(!ok) {
//			throw new RuntimeException("no se pudo cargar producto");
//		}
		// end prueba para tolerancia fallos hystrix
		// begin prueba sleep para timeout hystrix
		// debe ser mayor al valor del campo
		// hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds
//		try {
//			Thread.sleep(21000L);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// end prueba sleep para timeout hystrix

		return this.iproductoService.findById(id);
	}

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {
		return this.iproductoService.save(producto);
	}

	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto producto, @PathVariable Long id) {
		Producto productoDb = this.iproductoService.findById(id);

		productoDb.setNombre(producto.getNombre());
		productoDb.setPrecio(producto.getPrecio());
		return this.iproductoService.save(productoDb);
	}

	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		this.iproductoService.deleteById(id);
	}

}
