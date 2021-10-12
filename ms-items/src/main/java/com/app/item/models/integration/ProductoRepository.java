package com.app.item.models.integration;

import java.util.List;

import com.app.item.models.Producto;

public interface ProductoRepository {
	List<Producto> findAll();
	Producto findById(Long id);
	
	Producto save(Producto producto);
	Producto update(Producto producto,Long id);
	void delete(Long id);
}
