package com.blogapp.Services.Impl;

import java.io.IOException;
import java.util.Date;
import java.util.List; 
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogapp.Entities.Category;
import com.blogapp.Entities.Post;
import com.blogapp.Entities.User;
import com.blogapp.Exceptions.ResourceNotFoundException;
import com.blogapp.Payloads.PostDto;
import com.blogapp.Payloads.PostResponse;
import com.blogapp.Reposotries.CategoryRepo;
import com.blogapp.Reposotries.PostRepo;
import com.blogapp.Reposotries.UserRepo;
import com.blogapp.Services.FileService;
import com.blogapp.Services.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

//create the post 
	@Override
	public PostDto createPost(PostDto postDto,Integer id,Integer categoryId,MultipartFile file){
		User user = this.userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id)); 
		
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		Post post = this.mapper.map(postDto, Post.class);
		
		String uploadFile;
		try {
			uploadFile = this.fileService.uploadFile(path, file);
			post.setPimageName(uploadFile);
		} catch (IOException e){ 
			e.printStackTrace();
		} 
		post.setPDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post savePost = this.postRepo.save(post);
		return this.mapper.map(savePost, PostDto.class);
	}
	
//update the existing post
	@Override
	public PostDto updatePost(PostDto postDto, int pId) {
		Post post = this.postRepo.findById(pId).orElseThrow(() -> new ResourceNotFoundException("Post", "pId", pId));
		
		post.setPtitle(postDto.getPtitle());
		post.setPContent(postDto.getPContent());
		post.setPimageName(postDto.getPimageName());
		
		Post updatePost = this.postRepo.save(post);
		return this.mapper.map(updatePost, PostDto.class);
	}

//delete the post by id
	@Override
	public void deletepost(int pId) {
		 Post post = this.postRepo.findById(pId).orElseThrow(() -> new ResourceNotFoundException("Post", "pId", pId));
		 this.postRepo.delete(post);
	}

//get all the post avaliable
	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize, String sortBy, String sortDir) {
//		integer pageSize = 3;
//		integer pageNumber = 1;
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
													
												//alternative of above written line
//		if (sortDir.equalsIgnoreCase("asc")) {
//			sort = Sort.by(sortBy).ascending();
//		}else {
//			sort = Sort.by(sortBy).descending();
//		}
		
		Pageable page = PageRequest.of(pageNumber, pageSize, sort); //object of Pageable
		
		Page<Post> pagePost = this.postRepo.findAll(page);
		List<Post> allPost = pagePost.getContent();
		
		List<PostDto> postdtos = allPost.stream().map((posts) -> this.mapper.map(posts, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postdtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

//get the post by its id
	@Override
	public PostDto getPostById(int pId) {
		Post post = this.postRepo.findById(pId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "pId", pId));
		return this.mapper.map(post, PostDto.class);
	}

//get posts by category
	@Override
	public PostResponse getPostByCategory(int categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable page = PageRequest.of(pageNumber, pageSize, sort); 
		
		Page<Post> pagecat = this.postRepo.findByCategory(category,page); 
		List<Post> content = pagecat.getContent();
		
		List<PostDto> postdtos = content.stream().map((posts) -> this.mapper.map(posts, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postdtos);
		postResponse.setPageNumber(pagecat.getNumber());
		postResponse.setPageSize(pagecat.getSize());
		postResponse.setTotalElements(pagecat.getTotalElements());
		postResponse.setTotalPages(pagecat.getTotalPages());
		postResponse.setLastPage(pagecat.isLast());
		
		return postResponse;
	}
	
// get post by user
	@Override
	public PostResponse getPostByUser(int id, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable page = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost = this.postRepo.findByUser(user,page);
		List<Post> post = pagePost.getContent();
		
		List<PostDto> postdtos = post.stream().map((posts) -> this.mapper.map(posts, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postdtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}
 
//search post
	@Override
	public PostResponse searchPost(String keyword,Integer pageNumber, Integer pageSize) { 
		Pageable page = PageRequest.of(pageNumber, pageSize);
		
		Page<Post> pagePost = this.postRepo.findByPtitleContaining(keyword, page);
		List<Post> post = pagePost.getContent();
		
		List<PostDto> postDto = post.stream().map((t) -> this.mapper.map(t, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}	
	
}
