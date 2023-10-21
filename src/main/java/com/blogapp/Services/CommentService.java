package com.blogapp.Services;

import com.blogapp.Payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Integer pId, Integer id);
	
	void deleteComment(Integer comment_ID);
}
