package com.aTut.blog.security;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {
	private String secretKey ;
	public static final long  JWT_TOKEN_VALIDITY = 2 * 60 * 60;
	@Autowired
    public JwtTokenHelper() {
		String secretKey = "my_secret_key_for_bloggin_api_is_very_secret!!!!";
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
       
    }

	
	public String extractUsernameFromToken(String token) {
		return extractClaimFromToken(token, Claims::getSubject);
	}
	
	public Date extractExpirationFromToken(String token) {
		return extractClaimFromToken(token, Claims::getExpiration);
	}
	
	
	public  <T> T extractClaimFromToken(String token, Function<Claims,T>claimsResolver) {
		final Claims claims = extractAllClaimsFromToken(token); 
		return claimsResolver.apply(claims);
	}
	
	@SuppressWarnings("deprecation")
	private Claims extractAllClaimsFromToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(secretKey).build()
				.parseClaimsJws(token).getBody();
	}
	
	
	public String generateToken(UserDetails userDetails) {
		Map<String,Object>claims =new HashMap<>();
		return createToken(claims,userDetails.getUsername());
	}

	@SuppressWarnings("deprecation")
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS256 , secretKey).compact();
	 
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final  String username = extractUsernameFromToken(token);
		return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpirationFromToken(token).before(new Date());
	}
}
