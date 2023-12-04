package com.aTut.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aTut.blog.Payload.ApiResponse;
import com.aTut.blog.Payload.UserDto;
import com.aTut.blog.services.Implementation.UserServiceImpl;


@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserServiceImpl userService;
	
	// post -> create User
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createdUserDto = this.userService.createUser(userDto);
		
		return new ResponseEntity<UserDto>(createdUserDto , HttpStatus.CREATED);
	}
	
	//put -> update User
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userdto,@PathVariable int userId){
		UserDto updateUser = this.userService.updateUser(userdto, userId);
		return  ResponseEntity.ok(updateUser);
		
	}
	
	// get ->  get one
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable Integer userId){
		
		return ResponseEntity.ok(this.userService.getUser(userId));
	}
	// get > get All
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getUser(){
		
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	// delete -> delete user
		@PreAuthorize("hasRole('ADMIN')")
		@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@Valid @PathVariable int userId){
		 this.userService.deleteUser( userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Succefully",true), HttpStatus.OK);
		}

}
