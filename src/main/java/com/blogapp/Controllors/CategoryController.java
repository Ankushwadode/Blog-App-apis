package com.blogapp.Controllors;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.Payloads.ApiResponse;
import com.blogapp.Payloads.categoryDto;
import com.blogapp.Services.CategoryRepoService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryRepoService categoryRepoService;
	
//create category
	@PostMapping("/")
	public ResponseEntity<categoryDto> createCategory(@Valid @RequestBody categoryDto categorydto){
		categoryDto createCategory = this.categoryRepoService.createCategory(categorydto);
		return new ResponseEntity<categoryDto>(createCategory,HttpStatus.CREATED);
	}

//update category
	@PutMapping("/{catId}")
	public ResponseEntity<categoryDto> updateCategory(@Valid @RequestBody categoryDto categorydto,@PathVariable("catId") int Id){
		categoryDto updateCategory = this.categoryRepoService.updatecCategory(categorydto,Id);
		return new ResponseEntity<categoryDto>(updateCategory,HttpStatus.OK);
	}
	
//delete category
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("catId") int Id){
		this.categoryRepoService.deleteCategory(Id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted successfully...."),HttpStatus.OK);
	}
	
//get by id
	@GetMapping("/{catId}")
	public ResponseEntity<categoryDto> getByCategoryId(@PathVariable("catId") int Id){
		categoryDto categoryDto = this.categoryRepoService.getCategory(Id);
		return new ResponseEntity<categoryDto>(categoryDto,HttpStatus.OK);
	}
	
//get all category
//	@GetMapping("/")
//	public ResponseEntity<List<categoryDto>> getAllCategory(){
//		List<categoryDto> allCategory = this.categoryRepoService.getAllCategory();
//		return ResponseEntity.ok(allCategory);
//	}
	@GetMapping("/")
	public ResponseEntity<List<categoryDto>> getAllCategory(){
		List<categoryDto> allCategory = this.categoryRepoService.getAllCategory();
		return  ResponseEntity.ok(allCategory);
	}
}
