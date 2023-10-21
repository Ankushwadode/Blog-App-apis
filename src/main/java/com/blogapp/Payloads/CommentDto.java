package com.blogapp.Payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

	private Integer comment_ID;
	
	private Integer id;

	private String comment_Content;
}
