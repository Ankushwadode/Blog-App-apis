package com.blogapp.Reposotries;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.Entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
