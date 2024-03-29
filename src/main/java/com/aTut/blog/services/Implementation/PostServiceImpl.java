package com.aTut.blog.services.Implementation;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.aTut.blog.Payload.CategoryDto;
import com.aTut.blog.Payload.CommentDto;
import com.aTut.blog.Payload.PostDto;
import com.aTut.blog.Payload.PostResponse;
import com.aTut.blog.Payload.UserDto;
import com.aTut.blog.entities.Category;
import com.aTut.blog.entities.Comment;
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
	public PostDto createPost(PostDto postDto) {
		Post post = this.DtoToPost(postDto);
		post.setAdded_date(new Date());
		post.setImageName("default.jpg");
		
		Post createdPost = this.postRepo.save(post);
	
		return  this.PostToDto(createdPost);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		
		
		Category category = this.categoryRepo.findById(postDto.getCategoryDto().getCategoryId()).
				orElseThrow(()-> new ResourceNotFoundException("Category ", "category id", postDto.getCategoryDto().getCategoryId()));
		@SuppressWarnings("unused")
		User user =  this.userRepo.findById(postDto.getUserDto().getId()).
				orElseThrow(()-> new ResourceNotFoundException("user", "user id", postDto.getUserDto().getId()));
		
		post.setTitle(postDto.getTitle());
		if(!postDto.getImageName().equals(""))
			{post.setTitle(postDto.getImageName());}
		post.setContent(postDto.getContent());
		post.setCategory(category);
		
		return  this.PostToDto(this.postRepo.save(post));
		
		//TODO: user id check 

	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postID", postId));
		
		return this.PostToDto(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
	
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))? Sort.by(sortBy).ascending():  Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePost = this.postRepo.findAll(pageable);	
		List<Post> allPost = pagePost.getContent();
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(allPost.stream().map(post -> this.PostToDto(post)).collect(Collectors.toList()));
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastpage(pagePost.isLast());
		
		return postResponse;
	}

	// TODO: implement pagination and sorting in get post by user and getPost by category
	@Override
	public PostResponse getPostByUser(Integer userId,Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
		
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))? Sort.by(sortBy).ascending():  Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		List<Post> postByUser = this.postRepo.findByUser(user);
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postByUser.stream().map(post ->this.PostToDto(post)).collect(Collectors.toList()));
		Page<Post> pagePost = this.postRepo.findAll(pageable);	
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastpage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostResponse getPostByCategory(Integer categoryId,Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("asc"))? Sort.by(sortBy).ascending():  Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		List<Post> postByCategory = this.postRepo.findByCategory(category);
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postByCategory.stream().map(post ->this.PostToDto(post)).collect(Collectors.toList()));
		Page<Post> pagePost = this.postRepo.findAll(pageable);	
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastpage(pagePost.isLast());
		
		
		return postResponse;
	}

	@Override
	public PostResponse searchPosts(String keyword,Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
		List<Post> allPost= this.postRepo.searchTitleContaining("%" + keyword + "%");
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))? Sort.by(sortBy).ascending():  Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(allPost.stream().map(post ->this.PostToDto(post)).collect(Collectors.toList()));
		Page<Post> pagePost = this.postRepo.findAll(pageable);	
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastpage(pagePost.isLast());
		
		return postResponse ;
	}

	
	
	
	private PostDto PostToDto(Post post) {
		PostDto postdto = new PostDto();
		
		
		
		
		postdto.setPostId(post.getPostId());
		postdto.setTitle(post.getTitle());
		postdto.setContent(post.getContent());
		postdto.setAdded_date(post.getAdded_date());
		postdto.setImageName(post.getImageName());
		postdto.setCategoryDto(this.modelMapper.map(post.getCategory(),CategoryDto.class));
		postdto.setUserDto(this.modelMapper.map(post.getUser(),UserDto.class));
		
		postdto.setComments(post.getComments().stream().map(comment -> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toSet()));
		
		return postdto;
	}
	
	
	private Post DtoToPost(PostDto postDto) {
		
		
		Post newPost = new Post();
		Category category = this.categoryRepo.findById(postDto.getCategoryDto().getCategoryId()).
				orElseThrow(()-> new ResourceNotFoundException("Category ", "category id", postDto.getCategoryDto().getCategoryId()));
	
		User user =  this.userRepo.findById(postDto.getUserDto().getId()).
				orElseThrow(()-> new ResourceNotFoundException("user", "user id", postDto.getUserDto().getId()));
		
		newPost.setTitle(postDto.getTitle());
		newPost.setContent(postDto.getContent());
		newPost.setCategory(category);
		newPost.setUser(user);
		//checking if postDto has comments
		newPost.setComments(new HashSet<Comment>());
		
		
		
		return newPost;
	}
	
	
}
