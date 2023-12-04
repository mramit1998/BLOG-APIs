package com.aTut.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws   ServletException, IOException {
	
		
		//1 . get jwt token from request
		
		final String requestTokenHeader = request.getHeader("Authorization");
	
		
		String username = null;
		String jwtToken = null;
		
		// getting user name from token -> first get the token
		
		if(requestTokenHeader!=null  && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			
		try {
			username = jwtTokenHelper.extractUsernameFromToken(jwtToken);
		}
		catch(IllegalArgumentException e) {
			System.out.println("Unable to get JWT Token");	
		}
		catch(ExpiredJwtException e) {
			System.out.println("JWT Token has expired");	
		}
		}else {
			if(requestTokenHeader!=null) {
				System.out.println("JWT Token does not begin with Bearer String");
				}
		}
		
		//2. validate the token
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			//if token is validated
			if(jwtTokenHelper.validateToken(jwtToken, userDetails)) {
				
				//create Authentication obcject
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				
				//set details from the request
				usernamePasswordAuthenticationToken
				.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				//After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}else {
				System.out.println("invalid jwt token");
			}
		} 
		
		filterChain. doFilter(request, response);
	}

}
