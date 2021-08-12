package com.app.item.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
	private Producto producto;
	private Integer cantidad;

	public Double calcularTotal() {
		return this.producto.getPrecio() * this.cantidad.doubleValue();
	}

}
