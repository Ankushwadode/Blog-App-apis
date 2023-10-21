package com.blogapp.Services;

import java.util.List;

import com.blogapp.Payloads.categoryDto;

public interface CategoryRepoService {

//create category
	public categoryDto createCategory(categoryDto categoryDto);
	
//update category
	categoryDto updatecCategory(categoryDto categoryDto,Integer categoryId);
	
//delete category
	void deleteCategory(Integer categoryId);
	
//get single category
	categoryDto getCategory(Integer categoryId);
	
//get all category
	List<categoryDto> getAllCategory();
	
}
