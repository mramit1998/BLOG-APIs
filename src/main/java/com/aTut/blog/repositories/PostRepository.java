package com.aTut.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aTut.blog.entities.Category;
import com.aTut.blog.entities.Post;
import com.aTut.blog.entities.User;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);

}
