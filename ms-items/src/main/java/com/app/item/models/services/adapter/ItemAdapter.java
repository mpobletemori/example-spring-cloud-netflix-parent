package com.app.item.models.services.adapter;

import org.springframework.stereotype.Component;

import com.app.item.models.Item;
import com.app.commons.models.entity.Producto;

@Component
public class ItemAdapter {

	public Item toItem(Producto p) {
		return Item.builder().producto(p).cantidad(1).build();
	}

	public Item toItem2(Integer cantidad, Producto producto) {
		return Item.builder().producto(producto).cantidad(cantidad).build();
	}

}
