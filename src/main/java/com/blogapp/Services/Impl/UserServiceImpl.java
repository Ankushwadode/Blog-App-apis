package com.blogapp.Services.Impl;
 
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogapp.Config.HardValues;
import com.blogapp.Entities.Role;
import com.blogapp.Entities.User;
import com.blogapp.Exceptions.ResourceNotFoundException;
import com.blogapp.Payloads.UserDto;
import com.blogapp.Reposotries.RoleRepo;
import com.blogapp.Reposotries.UserRepo;
import com.blogapp.Services.UserService; 

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;  
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
//converting dto to user
//	private User dtoToUser(UserDto userDto) {
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
//		return user;
//	}
									//alternative
	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}
	
//converting user to dto
//	private UserDto userToDto(User user) {
//		UserDto userDto = new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
//		return userDto;
//	}
									//alternative
	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}
	
//register new user
	@Override
	public UserDto registerUser(UserDto userdto) { 
		User user = this.modelMapper.map(userdto, User.class);
		user.setPassword(this.encoder.encode(user.getPassword()));
		Role role = this.roleRepo.findById(HardValues.ROLE_NORMAL).get();
		user.getRoles().add(role);
		User registeredUser = this.userRepo.save(user);
		return this.modelMapper.map(registeredUser, UserDto.class);
	}
	
//create user
	@Override
	public UserDto createUser(UserDto userDto) {  
		User user = this.dtoToUser(userDto); 
		user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword())); 
		Role roles = this.roleRepo.findById(HardValues.ROLE_ADMIN).get();
		user.getRoles().add(roles);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}
//update user
	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = this.userToDto(updatedUser);
		return userDto1;
	}
//get by Id
	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
		return this.userToDto(user);
	}
//get all user
	@Override
	public List<UserDto> getAllUser() {
		 List<User> userList = this.userRepo.findAll();
		 List<UserDto> userDtoList = userList.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userDtoList;
	}
//delete user
	@Override
	public void deleteUser(Integer userId) { 
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
		this.userRepo.delete(user);
	}

}
