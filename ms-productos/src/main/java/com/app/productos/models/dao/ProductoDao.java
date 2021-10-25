package com.app.productos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.app.commons.models.entity.Producto;

public interface ProductoDao extends CrudRepository<Producto, Long> {

}
