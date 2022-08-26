package com.aTut.blog.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aTut.blog.Payload.JwtAuthRequest;
import com.aTut.blog.Payload.JwtAuthResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest request
			){
		return null;
	}
	
}
