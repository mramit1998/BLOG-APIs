package com.aTut.blog.services;

import java.util.List;

import com.aTut.blog.Payload.UserDto;




public interface UserService {

// create user
	UserDto createUser(UserDto user);
//update user
	UserDto updateUser(UserDto user, Integer userId);
	
// read user
	UserDto getUser(Integer userId);
	
// delete user
	void deleteUser(Integer userId);
	
// read all user
	List<UserDto> getAllUsers();
	
}
