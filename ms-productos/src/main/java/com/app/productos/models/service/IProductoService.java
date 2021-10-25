package com.app.productos.models.service;

import java.util.List;

import com.app.commons.models.entity.Producto;

public interface IProductoService {
	List<Producto> findAll();
	Producto findById(Long id);
	Producto save(Producto producto);
	void deleteById(Long id);
}
