package com.scrs.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.scrs.dto.AuthResponse;

public interface JwtService {
	
	 public String generateToken(AuthResponse authRes);
	 public String extractRole(String token);
	 public boolean validateToken(String token, UserDetails userDetails);
	String extractUserName(String token);
}
