package com.nhom2.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nhom2.entities.Product;

public interface ProductRepo extends CrudRepository<Product, Integer> {

	List<Product> findByNameContaining(String q);
	
	List<Product> findByPrice(long from, long to);

}
