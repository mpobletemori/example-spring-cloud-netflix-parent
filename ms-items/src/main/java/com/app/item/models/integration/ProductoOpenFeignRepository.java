package com.app.item.models.integration;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.item.models.Producto;

@FeignClient(name = "${app.ms-productos.id}")
public interface ProductoOpenFeignRepository {

	@GetMapping("/listar")
	List<Producto> findAll();

	@GetMapping("/ver/{id}")
	Producto findById(@PathVariable Long id);

	@PostMapping("/crear")
	Producto save(@RequestBody Producto producto);

	@PutMapping("/editar/{id}")
	Producto update(@RequestBody Producto producto, @PathVariable Long id);

	@DeleteMapping("/eliminar/{id}")
	void delete(@PathVariable Long id);

}
