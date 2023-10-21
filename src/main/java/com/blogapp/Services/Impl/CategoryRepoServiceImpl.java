package com.blogapp.Services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.Entities.Category;
import com.blogapp.Exceptions.ResourceNotFoundException;
import com.blogapp.Payloads.categoryDto;
import com.blogapp.Reposotries.CategoryRepo;
import com.blogapp.Services.CategoryRepoService;

@Service
public class CategoryRepoServiceImpl implements CategoryRepoService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper mapper;

//create
	@Override
	public categoryDto createCategory(categoryDto categorydto) {
		Category category = this.mapper.map(categorydto, Category.class);
		Category addedCategory = this.categoryRepo.save(category);
		return this.mapper.map(addedCategory, categoryDto.class);
	}

//update
	@Override
	public categoryDto updatecCategory(categoryDto categorydto, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
						.orElseThrow(() -> new ResourceNotFoundException("Category", "Catergory Id", categoryId));
		category.setCategoryTitle(categorydto.getCategoryTitle());
		category.setCategoryDescription(categorydto.getCategoryDescription());
		Category updatedCategory = this.categoryRepo.save(category);
		return this.mapper.map(updatedCategory, categoryDto.class);
	}

//delete
	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
						.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		this.categoryRepo.delete(category);

	}

//get single
	@Override
	public categoryDto getCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		return this.mapper.map(category, categoryDto.class);
	}

//get all
	@Override
	public List<categoryDto> getAllCategory() {
		List<Category> categories = this.categoryRepo.findAll();
		List<categoryDto> categorydtos = categories.stream()
									.map((category) -> this.mapper.map(category, categoryDto.class)).collect(Collectors.toList());
		return categorydtos;
	}

}
