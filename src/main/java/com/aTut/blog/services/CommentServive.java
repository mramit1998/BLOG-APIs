package com.aTut.blog.services;

import com.aTut.blog.Payload.CommentDto;

public interface CommentServive {

	CommentDto createComment(CommentDto commentDto, Integer postId);
	void deleteComment(Integer commentId);
	
	// edit comment
}
