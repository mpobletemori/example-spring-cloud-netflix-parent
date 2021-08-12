/**
 * 
 */
package com.app.item.models.integration;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.app.item.models.Producto;

/**
 * @author mpobletemori
 *
 */

@Component
public class ProductoRepositoryImpl implements ProductoRepository {

	@Value("${app.ms-productos.uri.listar}")
	private String appMsProductosUriListar;
	
	@Value("${app.ms-productos.uri.ver}")
	private String appMsProductosUriVer;

	private RestTemplate clienteRest;

	public ProductoRepositoryImpl(RestTemplate clienteRest) {
		this.clienteRest = clienteRest;
	}

	@Override
	public List<Producto> findAll() {
		return List.of(this.clienteRest.getForObject(this.appMsProductosUriListar, Producto[].class));
	}

	@Override
	public Producto findById(Long id) {
		return this.clienteRest.getForObject(this.appMsProductosUriVer, Producto.class, Map.of("id", id.toString()));
	}

}
