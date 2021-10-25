package com.app.item.models.services;

import java.util.List;

import com.app.item.models.Item;
import com.app.commons.models.entity.Producto;

public interface ItemService {
	List<Item> findAll();

	Item findById(Long id, Integer cantidad);
	
	Producto save(Producto producto);
	Producto update(Producto producto,Long id);
	void delete(Long id);
}
