package com.blogapp.Services;
 
import java.util.List;

import com.blogapp.Payloads.UserDto;

public interface UserService {
	
	UserDto registerUser(UserDto user);

	UserDto createUser(UserDto userDto); //create the user
	
	UserDto updateUser(UserDto userDto,Integer userId); //update the existing user
	
	UserDto getUserById(Integer userId); //gets the user by its id
	
	List<UserDto> getAllUser(); //gets all the user 
	
	void deleteUser(Integer userId); //delete the user by id
	
}
