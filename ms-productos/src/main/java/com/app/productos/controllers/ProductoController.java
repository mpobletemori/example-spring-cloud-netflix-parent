package com.app.productos.controllers;

import java.util.List;

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
	public Producto detalle(@PathVariable Long id) {
		return this.iproductoService.findById(id);
	}

}
