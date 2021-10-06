package com.app.productos.controllers;

import java.util.List;
//import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.app.productos.models.entity.Producto;
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
		
		//begin pruebas con resilience4j
//		if(id.equals(10L)) {
//			throw new IllegalStateException("Producto no encontrado!");			
//		}
//		if(id.equals(7L)) {
//			TimeUnit.SECONDS.sleep(5L);
//		}
		//end pruebas con resilience4j
		
		// begin prueba para tolerancia fallos hystrix
//		boolean ok = false;
//		if(!ok) {
//			throw new RuntimeException("no se pudo cargar producto");
//		}
		// end prueba para tolerancia fallos hystrix
		// begin prueba sleep para timeout hystrix
		//debe ser mayor al valor del campo hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds
//		try {
//			Thread.sleep(21000L);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// end prueba sleep para timeout hystrix
		
		return this.iproductoService.findById(id);
	}

}
