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

	private ProductoOpenFeignRepository productoOpenFeignRepository;

	public ProductoRepositoryImpl(ProductoOpenFeignRepository productoOpenFeignRepository) {
		this.productoOpenFeignRepository = productoOpenFeignRepository;
	}

	@Override
	public List<Producto> findAll() {
		return this.productoOpenFeignRepository.findAll();
	}

	@Override
	public Producto findById(Long id) {
		return this.productoOpenFeignRepository.findById(id);
	}

}
