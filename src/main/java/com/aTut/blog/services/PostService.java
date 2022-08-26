package com.aTut.blog.services;

import java.util.List;

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
	List<PostDto> getPostByUser(Integer userId);
	
	//getAllPostByCategory
	List<PostDto> getPostByCategory(Integer categoryId);
	
	List<PostDto> searchPosts(String keyword);
	
}
