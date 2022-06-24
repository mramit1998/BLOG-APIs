package com.aTut.blog.services;

import java.util.List;

import com.aTut.blog.Payload.PostDto;
import com.aTut.blog.entities.Post;

public interface PostService {
	
	//add post 
	
	PostDto createPost(PostDto postDto,Integer categoryId , Integer userId);
	
	//delete post
	void deletePost(Integer id);
	
	// update post 
	Post updatePost(PostDto postDto,Integer postId);
	
	//getPost
	Post getPostById(Integer id);
	
	//getAllPost
	List<Post> getAllPost();
	
	
	//getAllpostbyuser
	List<PostDto> getPostByUser(Integer userId);
	
	//getAllPostByCategory
	List<PostDto> getPostByCategory(Integer categoryId);
	
	List<Post> searchPosts(String keyword);
	
}
