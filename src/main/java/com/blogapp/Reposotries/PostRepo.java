package com.blogapp.Reposotries;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.Entities.Category;
import com.blogapp.Entities.Post;
import com.blogapp.Entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	Page<Post> findByUser(User user, Pageable pageable);
	
	Page<Post> findByCategory(Category category, Pageable pageable);
	
	Page<Post> findByPtitleContaining(String ptitle, Pageable pageable);
}