/**
 * 
 */
package com.app.productos.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.productos.models.dao.ProductoDao;
import com.app.productos.models.entity.Producto;

/**
 * @author mpobletemori
 *
 */
@Transactional(readOnly = true)
@Service
public class ProductoServiceImpl implements IProductoService {

	private ProductoDao productoDao;
	
	private Environment env;
	
	@Value("${server.port}")
	private Integer port;

	public ProductoServiceImpl(ProductoDao productoDao,Environment env) {
		this.productoDao = productoDao;
		this.env= env;
	}

	@Override
	public List<Producto> findAll() {
		// TODO Auto-generated method stub
		return ((List<Producto>) this.productoDao.findAll()).stream().map(producto -> {
			producto.setPort(retrievePort());
			return producto;
		}).collect(Collectors.toList());
	}

	@Override
	public Producto findById(Long id) {
		// TODO Auto-generated method stub
		Producto prod = this.productoDao.findById(id).orElse(null);
		prod.setPort(retrievePort());
		return prod;
	}
	
	private Integer retrievePort() {
		return Integer.parseInt(this.env.getProperty("local.server.port"));
		//return port;
	}

}
