package com.aTut.blog.Payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {


	private int id;
	
	private String content;
	
	private Integer user_commented;
}
