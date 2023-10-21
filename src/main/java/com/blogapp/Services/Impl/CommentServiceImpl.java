package com.blogapp.Services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.Entities.Comment;
import com.blogapp.Entities.Post;
import com.blogapp.Entities.User;
import com.blogapp.Exceptions.ResourceNotFoundException;
import com.blogapp.Payloads.CommentDto;
import com.blogapp.Reposotries.CommentRepo;
import com.blogapp.Reposotries.PostRepo;
import com.blogapp.Reposotries.UserRepo;
import com.blogapp.Services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer pId, Integer Id) {
		
		User user = this.userRepo.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", Id)); 
		
		Post post = this.postRepo.findById(pId).orElseThrow(() -> new ResourceNotFoundException("Post", "pId", pId));
		
		Comment comment = this.mapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		Comment saveComment = this.commentRepo.save(comment);
		
		return this.mapper.map(saveComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer comment_ID) {
		Comment comment = this.commentRepo.findById(comment_ID)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "comment_ID", comment_ID));
		
		this.commentRepo.delete(comment);
	}

}
