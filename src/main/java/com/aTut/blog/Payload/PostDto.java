package com.aTut.blog.Payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	private Integer postId;
	
	private String title;
	
	private String content;
	
	
	private String imageName;
	
	private Date added_date;
//private CategoryDto category;
//
//private UserDto user;

//categoryId
	@NotNull
	private Integer categoryId;
//user id
	@NotNull
	private Integer userId;
	
	private String userName;
	
	private String categoryName;
	
	private Set<CommentDto> comments = new HashSet<>();
}

