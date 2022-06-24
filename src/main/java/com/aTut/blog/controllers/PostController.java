package com.aTut.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aTut.blog.Payload.PostDto;
import com.aTut.blog.services.Implementation.PostServiceImpl;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostServiceImpl postService;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts/")
	public ResponseEntity<PostDto> createCategory( @RequestBody PostDto postDto , 
													@PathVariable Integer userId ,@PathVariable Integer categoryId ){
		
		PostDto createPost = this.postService.createPost(postDto, categoryId, userId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
		List<PostDto> postsByUser = this.postService.getPostByUser(userId);
		
		return ResponseEntity.ok(postsByUser);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
		List<PostDto> postsByCategory = this.postService.getPostByCategory(categoryId);
		
		return ResponseEntity.ok(postsByCategory);
	}
	
}
