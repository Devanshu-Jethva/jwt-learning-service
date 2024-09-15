package com.jwt.services.impl;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jwt.entities.User;
import com.jwt.services.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

	@Value("${jwt.secret}")
	private String jwtSecretKey;

	@Override
	public String generateAccessToken(final User user) {
		return Jwts.builder().subject(user.getId().toString()).claim("email", user.getEmail())
				.claim("roles", user.getRoles()).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)).signWith(getSecretKey()).compact();
	}

	@Override
	public String generateRefreshToken(final User user) {
		return Jwts.builder().subject(user.getId().toString()).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 1000l * 60 * 60 * 24 * 30 * 6))
				.signWith(getSecretKey()).compact();
	}

	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public Long getUserIdFromToken(final String token) {
		Claims claims = Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
		return Long.valueOf(claims.getSubject());
	}

}
