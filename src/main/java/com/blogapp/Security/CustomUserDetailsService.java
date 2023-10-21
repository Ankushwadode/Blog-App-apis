package com.blogapp.Security;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException; 
import org.springframework.stereotype.Service;
 
import com.blogapp.Entities.User;
import com.blogapp.Exceptions.ResourceNotFoundException;
import com.blogapp.Reposotries.UserRepo;
 

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		//small adjustment in this line to handle exception to avoid this create new custom exception
		User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email ", email));
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found...");
		}
		
		return new org.springframework.security.core.userdetails.User
				(user.getEmail(),user.getPassword(),user.getAuthorities());
	}
}
