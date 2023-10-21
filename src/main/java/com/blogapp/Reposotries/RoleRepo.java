package com.blogapp.Reposotries;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.Entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
