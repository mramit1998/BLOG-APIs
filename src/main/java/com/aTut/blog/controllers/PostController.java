package com.aTut.blog.controllers;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aTut.blog.Payload.ApiResponse;
import com.aTut.blog.Payload.PostDto;
import com.aTut.blog.Payload.PostResponse;
import com.aTut.blog.services.Implementation.FileServiceImpl;
import com.aTut.blog.services.Implementation.PostServiceImpl;
import com.aTut.blog.util.AppConstants;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostServiceImpl postService;
	
	@Autowired
	private FileServiceImpl fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/posts/")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto ){
		PostDto createPost = this.postService.createPost(postDto);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return ResponseEntity.ok(new ApiResponse("Post deleted successfully", true));
	}
	
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost( @PathVariable Integer postId,@Valid @RequestBody PostDto postDto){
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPostDto, HttpStatus.OK);
	}
	
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto , HttpStatus.OK);
	}
	
	@GetMapping("/posts/")
	public ResponseEntity <PostResponse> getAllPosts(
			@RequestParam(value ="pageNumber" , defaultValue = AppConstants.PAGE_NUMBER, required = false)Integer pageNumber,
			@RequestParam(value ="pageSize" , defaultValue = AppConstants.PAGE_SIZE, required = false)Integer pageSize,
			@RequestParam(value ="sortBy" , defaultValue = AppConstants.SORT_BY_POST, required = false)String sortBy,
			@RequestParam(value ="sortDir" , defaultValue = AppConstants.SORT_DIR, required = false)String sortDir){
		PostResponse allPosts = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return ResponseEntity.ok(allPosts);
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostByUser(@PathVariable Integer userId,
			@RequestParam(value ="pageNumber" , defaultValue = AppConstants.PAGE_NUMBER, required = false)Integer pageNumber,
			@RequestParam(value ="pageSize" , defaultValue = AppConstants.PAGE_SIZE, required = false)Integer pageSize,
			@RequestParam(value ="sortBy" , defaultValue = AppConstants.SORT_BY_POST, required = false)String sortBy,
			@RequestParam(value ="sortDir" , defaultValue = AppConstants.SORT_DIR, required = false)String sortDir){
				PostResponse allPostsByUser = this.postService.getPostByUser(userId,pageNumber,pageSize,sortBy,sortDir);
		return ResponseEntity.ok(allPostsByUser);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostByCategory(@PathVariable Integer categoryId,
			@RequestParam(value ="pageNumber" , defaultValue = AppConstants.PAGE_NUMBER, required = false)Integer pageNumber,
			@RequestParam(value ="pageSize" , defaultValue = AppConstants.PAGE_SIZE, required = false)Integer pageSize,
			@RequestParam(value ="sortBy" , defaultValue = AppConstants.SORT_BY_POST, required = false)String sortBy,
			@RequestParam(value ="sortDir" , defaultValue = AppConstants.SORT_DIR, required = false)String sortDir){
		PostResponse postsByCategory = this.postService.getPostByCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
		
		return ResponseEntity.ok(postsByCategory);
	}
	
	@GetMapping("/posts/search?{keywords}")
	public ResponseEntity<PostResponse> searchPost(@PathVariable String keywords,
			@RequestParam(value ="pageNumber" , defaultValue = AppConstants.PAGE_NUMBER, required = false)Integer pageNumber,
			@RequestParam(value ="pageSize" , defaultValue = AppConstants.PAGE_SIZE, required = false)Integer pageSize,
			@RequestParam(value ="sortBy" , defaultValue = AppConstants.SORT_BY_POST, required = false)String sortBy,
			@RequestParam(value ="sortDir" , defaultValue = AppConstants.SORT_DIR, required = false)String sortDir){
		PostResponse postsBykeywords = this.postService.searchPosts(keywords,pageNumber,pageSize,sortBy,sortDir);
		return ResponseEntity.ok(postsBykeywords);
	}
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<String> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException{
		
			PostDto postDto = this.postService.getPostById(postId);
			
			String filename = this.fileService.uploadImage(path, image);
			
			postDto.setImageName(filename);
			
			this.postService.updatePost(postDto, postId);
			
			return new ResponseEntity<String>(filename + " uploaded successfully",HttpStatus.OK);
			
	}
	
	@GetMapping(value ="/post/image/{imageName}" ,produces = MediaType.IMAGE_JPEG_VALUE )
	public void downloadImage(
			@PathVariable String imageName,
			HttpServletResponse httpServletResponse )	throws IOException
	{
		InputStream resource = this.fileService.getResource(path, imageName);
		httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, httpServletResponse.getOutputStream());
	}
}
