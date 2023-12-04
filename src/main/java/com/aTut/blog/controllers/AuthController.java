package com.aTut.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aTut.blog.Payload.JwtAuthRequest;
import com.aTut.blog.Payload.JwtAuthResponse;
import com.aTut.blog.Payload.UserDto;
import com.aTut.blog.exceptions.ApiException;
import com.aTut.blog.security.CustomUserDetailsService;
import com.aTut.blog.security.JwtTokenHelper;
import com.aTut.blog.services.Implementation.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenHelper tokenHelper;
	
	@Autowired
	private CustomUserDetailsService userDetailsService; 
	
	@Autowired
	private UserServiceImpl userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest request) throws Exception{
		
		this.authenticate(request.getUsername(),request.getPassword());
		
//		once authenticated ->generate token
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		
	
		String generatedToken = this.tokenHelper.generateToken(userDetails);
		
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setJwttoken(generatedToken);
		
		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse,HttpStatus.OK);
		
	}
	
	private void authenticate(String username,String password) throws Exception {
		//create usernamePasswordAuth token
		UsernamePasswordAuthenticationToken  authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
		try {
			this.authenticationManager.authenticate(authenticationToken);	
		} catch (BadCredentialsException e) {
			throw new ApiException("Invalid username password");
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto dto){
		
		UserDto registeredUser = this.userService.registerUser(dto);
		
		
		return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
	}
	
}
