package com.blogapp.Reposotries;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.Entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	Optional<User> findByEmail(String email);
	
}
