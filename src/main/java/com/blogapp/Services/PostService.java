package com.blogapp.Services;

import java.io.IOException; 

import org.springframework.web.multipart.MultipartFile;

import com.blogapp.Payloads.PostDto;
import com.blogapp.Payloads.PostResponse; 

public interface PostService {

//create post
	PostDto createPost(PostDto postDto,Integer id,Integer categoryId,MultipartFile file) throws IOException;
	
//update post
	PostDto updatePost(PostDto postDto, int pId);
	
//delete post
	void deletepost(int pId);
	
//get all post
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
//get single post
	PostDto getPostById(int pId);
	
//get all post by category
	PostResponse getPostByCategory(int categoryId,Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
//get all post by user
	PostResponse getPostByUser(int id, Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
	
//search post
	PostResponse searchPost(String keyword,Integer pageNumber, Integer pageSize);
	
}
