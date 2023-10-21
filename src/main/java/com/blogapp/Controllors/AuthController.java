package com.blogapp.Controllors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; 
import com.blogapp.Payloads.UserDto;
import com.blogapp.Security.CustomUserDetailsService;
import com.blogapp.Security.JwtAuthRequest;
import com.blogapp.Security.JwtAuthResponse;
import com.blogapp.Security.JwtTokenHelper;
import com.blogapp.Services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	//generates the jwt token
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request){
		
	this.authenticate(request.getEmail(),request.getPassword());
	
	UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(request.getEmail());
	
	String generateToken = this.jwtTokenHelper.generateToken(userDetails.getUsername());
	
	JwtAuthResponse authResponse = new JwtAuthResponse();
	authResponse.setToken(generateToken);
		
		return new ResponseEntity<JwtAuthResponse>(authResponse,HttpStatus.OK);
		
	}

	private void authenticate(String email, String password) {
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
		this.authenticationManager.authenticate(usernamePasswordAuthenticationToken); 
		
	}
	
//register new user api
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		UserDto registerUser = this.userService.registerUser(userDto);
		return new ResponseEntity<UserDto>(registerUser,HttpStatus.CREATED);
	}
	
}
