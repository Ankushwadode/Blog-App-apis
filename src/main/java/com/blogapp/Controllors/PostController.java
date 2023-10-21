package com.blogapp.Controllors;

import java.io.IOException;
import java.io.InputStream; 

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; 
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogapp.Config.HardValues;
import com.blogapp.Entities.Post;
import com.blogapp.Payloads.ApiResponse;
import com.blogapp.Payloads.PostDto;
import com.blogapp.Payloads.PostResponse;
import com.blogapp.Payloads.categoryDto;
import com.blogapp.Services.FileService;
import com.blogapp.Services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

//create
	@PostMapping(value = "/user/{id}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@ModelAttribute PostDto postDto, 
			@PathVariable Integer id, 
			@PathVariable Integer categoryId,
			@RequestParam("image") MultipartFile file) throws IOException{
		PostDto createPost = this.postService.createPost(postDto, id, categoryId, file);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
//get by user 
	@GetMapping("/user/{id}/posts")
	public ResponseEntity<PostResponse> getPostByUser(
			@PathVariable Integer id,
			@RequestParam(value = "pageNumber",defaultValue = HardValues.PAGE_NUMBER,required = false) Integer pageNumber,/*page number start from zero*/
			@RequestParam(value = "pageSize", defaultValue = HardValues.PAGE_SIZE, required = false)Integer pageSize,/*number of records on single page*/			
			@RequestParam(value = "sortBy",defaultValue = HardValues.SORT_BY, required = false) String sortBy,/*sort acc to the field provided*/
			@RequestParam(value = "sortDir",defaultValue = HardValues.SORT_DIR, required = false) String sortDir/*sort either ascending or descending order*/
			){
		PostResponse userPosts = this.postService.getPostByUser(id,pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(userPosts,HttpStatus.OK);
	}
	
//get by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostByCategory(
			@PathVariable Integer categoryId,
			@RequestParam(value = "pageNumber",defaultValue = HardValues.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = HardValues.PAGE_SIZE, required = false)Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = HardValues.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = HardValues.SORT_DIR, required = false) String sortDir
			){
		PostResponse categoryPosts = this.postService.getPostByCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(categoryPosts,HttpStatus.OK);
	}
	
// get all post
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber",defaultValue = HardValues.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = HardValues.PAGE_SIZE, required = false)Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = HardValues.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = HardValues.SORT_DIR, required = false) String sortDir
			){
		PostResponse allPosts = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(allPosts,HttpStatus.OK);
	}
	
//get post by post id
	@GetMapping("/post/{pId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer pId){
		PostDto postById = this.postService.getPostById(pId);
		return new ResponseEntity<PostDto>(postById,HttpStatus.OK);
	}

//delete post 
	@DeleteMapping("/{pId}/posts")
	public ApiResponse deletePost(@PathVariable Integer pId) {
		this.postService.deletepost(pId);
		return new ApiResponse("Post is deleted successfully...");
	}
	
//update post
	@PutMapping("/{pId}/posts")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postdto,@PathVariable Integer pId) {
		PostDto updatePost = this.postService.updatePost(postdto, pId); 
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}	
	
//search post
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<PostResponse> searchPostByTitle(
			@PathVariable String keyword,
			@RequestParam(value = "pageNumber",defaultValue = HardValues.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = HardValues.PAGE_SIZE, required = false)Integer pageSize
			){
		PostResponse results = this.postService.searchPost(keyword,pageNumber,pageSize);
		return new ResponseEntity<PostResponse>(results,HttpStatus.OK);
	}

//image upload
	@PostMapping("/posts/image/upload/{pId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile file,
			@PathVariable Integer pId) throws IOException{
		
		PostDto post = this.postService.getPostById(pId);
		String fileName = this.fileService.uploadFile(path, file);
		post.setPimageName(fileName);
		PostDto updatePost = this.postService.updatePost(post, pId);
		
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
// to serve files
	@GetMapping(value = "/posts/image/{image}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String image, HttpServletResponse response) throws IOException{
		InputStream stream = this.fileService.getResource(path, image);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(stream, response.getOutputStream());
	}
	
	

}
