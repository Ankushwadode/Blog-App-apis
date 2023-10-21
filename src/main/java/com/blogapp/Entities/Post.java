package com.blogapp.Entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pId;
	
	@Column(length = 100,nullable = false,name = "post_title")
	private String ptitle;
	
	@Column(name = "post_content",length = 10000)
	private String pContent;
	
	@Column(name = "post_imagename")
	private String pimageName;

	@Column(name = "post_date")
	private Date pDate;
	
	@ManyToOne //join column created by category and category id to change we use join column annotation here
	private Category category;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<Comment> comments = new HashSet<>();
}
