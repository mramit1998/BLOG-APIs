package com.aTut.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aTut.blog.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	

}
