package com.aTut.blog.services.Implementation;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aTut.blog.Payload.PostDto;
import com.aTut.blog.entities.Category;
import com.aTut.blog.entities.Post;
import com.aTut.blog.entities.User;
import com.aTut.blog.exceptions.ResourceNotFoundException;
import com.aTut.blog.repositories.CategoryRepository;
import com.aTut.blog.repositories.PostRepository;
import com.aTut.blog.repositories.UserRepository;
import com.aTut.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, Integer categoryId, Integer userId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

		Post newPost = this.modelMapper.map(postDto, Post.class);

		newPost.setAdded_date(new Date());
		newPost.setCategory(category);
		newPost.setUser(user);
		newPost.setImageName("default.jpg");

		Post createdPost = this.postRepo.save(newPost);

		return this.modelMapper.map(createdPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		this.postRepo.delete(post);
	}

	@Override
	public Post updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		post.setTitle(postDto.getTitle());

		return null;
	}

	@Override
	public Post getPostById(Integer postId) {
		return null;
	}

	@Override
	public List<Post> getAllPost() {
		return null;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		List<Post> postByUser = this.postRepo.findByUser(user);

		
		return postByUser.stream().map(post -> this.modelMapper.map(post, PostDto.class) ).collect(Collectors.toList());
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		List<Post> postByCategory = this.postRepo.findByCategory(category);

		
		return postByCategory.stream().map(post -> this.modelMapper.map(post, PostDto.class) ).collect(Collectors.toList());
	}

	@Override
	public List<Post> searchPosts(String keyword) {
		return null; 
	}

	// TODO : Instead of modelmapper use custom methods to not expose everything by
	// any post

}
