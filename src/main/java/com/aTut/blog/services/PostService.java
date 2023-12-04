package com.aTut.blog.services;

import com.aTut.blog.Payload.PostDto;
import com.aTut.blog.Payload.PostResponse;


public interface PostService {
	
	//add post 
	
	PostDto createPost(PostDto postDto);
	
	//delete post
	void deletePost(Integer id);
	
	// update post 
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//getPost
	PostDto getPostById(Integer id);
	
	//getAllPost
	PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
	
	
	//getAllpostbyuser
	PostResponse getPostByUser(Integer userId,Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
	
	//getAllPostByCategory
	PostResponse getPostByCategory(Integer categoryId,Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
	
	

	PostResponse searchPosts(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
}
