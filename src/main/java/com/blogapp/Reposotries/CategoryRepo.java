package com.blogapp.Reposotries;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.Entities.Category; 

public interface CategoryRepo extends JpaRepository<Category, Integer>{
	
	
	
}
