package com.blogapp.Security;

import lombok.Data;

@Data
public class JwtAuthRequest {

	private String email;
	
	private String password;
	
}
