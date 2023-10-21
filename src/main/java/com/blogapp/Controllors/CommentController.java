package com.blogapp.Controllors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.Payloads.ApiResponse;
import com.blogapp.Payloads.CommentDto;
import com.blogapp.Services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;

//add comment to post
	@PostMapping("/posts/{pId}/user/{Id}/comment")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable Integer pId, @PathVariable Integer Id){
		CommentDto createComment = this.commentService.createComment(comment, pId,Id);
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
	}

//delete comment from post
	@DeleteMapping("/comment/{comment_ID}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer comment_ID){
		this.commentService.deleteComment(comment_ID);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully..."),HttpStatus.OK);
	}
	
}
