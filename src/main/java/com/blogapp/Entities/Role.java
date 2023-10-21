package com.blogapp.Entities; 

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id; 
import javax.persistence.Table;

import lombok.Data; 

@Entity 
@Table(name = "roles")
@Data
public class Role {

	@Id 
	private Integer id;

	private String name;

}
