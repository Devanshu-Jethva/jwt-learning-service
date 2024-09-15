package com.jwt.services;

import com.jwt.entities.User;

public interface JwtService {

	String generateAccessToken(User user);

	Long getUserIdFromToken(String token);

	String generateRefreshToken(User user);

}
