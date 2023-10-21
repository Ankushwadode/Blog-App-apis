package com.blogapp.Controllors;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.Payloads.ApiResponse;
import com.blogapp.Payloads.UserDto;
import com.blogapp.Services.UserService;

@RestController
@RequestMapping("/api/users") 
public class UserController {

	@Autowired
	private UserService userService;

//create user 

	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createdUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
	}

//update user

	@PutMapping("/{userid}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userid") int uId) {
		UserDto updateUserDto = this.userService.updateUser(userDto, uId);
		// return new ResponseEntity<>(updateUserDto,HttpStatus.OK);
		return ResponseEntity.ok(updateUserDto);
	}

//delete user  

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{userid}")  
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userid") int uid) {
		this.userService.deleteUser(uid);
		// return ResponseEntity.status(HttpStatus.OK).body("user deleted successfully.....");
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully..."), HttpStatus.OK);
	}

//get multiple user --Admin

	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser() {
		return ResponseEntity.ok(this.userService.getAllUser());
	}

//get single user --admin

	@GetMapping("/{userid}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userid") int uId) {
		return ResponseEntity.ok(this.userService.getUserById(uId));
	}

}
