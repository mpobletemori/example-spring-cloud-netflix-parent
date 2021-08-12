/**
 * 
 */
package com.app.item.models.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.item.models.Item;
import com.app.item.models.integration.ProductoRepository;
import com.app.item.models.services.adapter.ItemAdapter;

/**
 * @author mpobletemori
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	private ProductoRepository productoRepository;
	private ItemAdapter itemAdapter;

	public ItemServiceImpl(ProductoRepository productoRepository, ItemAdapter itemAdapter) {
		this.productoRepository = productoRepository;
		this.itemAdapter = itemAdapter;
	}

	@Override
	public List<Item> findAll() {
		return this.productoRepository.findAll().stream().map(p -> this.itemAdapter.toItem(p))
				.collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		return this.itemAdapter.toItem2(cantidad, this.productoRepository.findById(id));
	}

}
