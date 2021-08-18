package com.app.item.models.integration;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.app.item.models.Producto;

@FeignClient(name = "${app.ms-productos.id}")
public interface ProductoOpenFeignRepository {
	@GetMapping("/listar")
	List<Producto> findAll();

	@GetMapping("/ver/{id}")
	Producto findById(@PathVariable Long id);
}
