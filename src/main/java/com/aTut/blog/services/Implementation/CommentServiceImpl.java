package com.aTut.blog.services.Implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aTut.blog.Payload.CommentDto;
import com.aTut.blog.entities.Comment;
import com.aTut.blog.entities.Post;
import com.aTut.blog.exceptions.ResourceNotFoundException;
import com.aTut.blog.repositories.CommentRepository;
import com.aTut.blog.repositories.PostRepository;
import com.aTut.blog.services.CommentServive;


@Service
public class CommentServiceImpl implements CommentServive {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ModelMapper modelMapper;
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post =this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		
		Comment comment = new Comment();
		comment.setContent(commentDto.getContent());
		comment.setPost(post);
		
		Comment CreatedComment = this.commentRepository.save(comment);
		
		
		return this.modelMapper.map(CreatedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
		this.commentRepository.delete(comment);
	}

}
