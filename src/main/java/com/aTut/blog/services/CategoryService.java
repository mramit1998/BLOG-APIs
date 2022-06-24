package com.aTut.blog.services;

import java.util.List;

import com.aTut.blog.Payload.CategoryDto;

public interface CategoryService {
	
	//create category
	public CategoryDto addCategory(CategoryDto categoryDto);
	
	//getCategories
	public CategoryDto getCategory(Integer categoryId);
	
	//getAllCategories
	public List<CategoryDto> getAllCategory();
	
	//update categories
	public CategoryDto updateCategory(CategoryDto categoryDto);

	//delete Categories
	public void deleteCategory(Integer categoryId);
}
