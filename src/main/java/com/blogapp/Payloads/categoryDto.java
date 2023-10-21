package com.blogapp.Payloads;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class categoryDto {

	private int categoryId;
	
	@NotBlank
	private String categoryTitle;
	
	@NotBlank
	private String categoryDescription;
	
}
