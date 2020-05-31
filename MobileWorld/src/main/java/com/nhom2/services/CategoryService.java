package com.nhom2.services;

import java.util.List;

import com.nhom2.entities.Category;

public interface CategoryService {
	
	Iterable<Category> findAll();

    List<Category> search(String q);

    Category findOne(int id);

    void save(Category category);

    void delete(int id);
	
}
