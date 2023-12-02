package com.aTut.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aTut.blog.Payload.CommentDto;
import com.aTut.blog.services.Implementation.CommentServiceImpl;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentServiceImpl serviceImpl;

	@PostMapping("/post/{postId}/comments/")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentdto, @PathVariable Integer postId
			){
		CommentDto createdComment = this.serviceImpl.createComment(commentdto, postId);
		
		return new ResponseEntity<CommentDto>(createdComment , HttpStatus.OK);
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Integer commentId){
		this.serviceImpl.deleteComment(commentId);
		
		return new ResponseEntity<String>(commentId + " is deleted succesfully" , HttpStatus.OK);
	}
}
