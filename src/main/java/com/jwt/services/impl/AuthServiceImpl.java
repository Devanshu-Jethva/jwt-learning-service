package com.jwt.services.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.jwt.dto.LoginDto;
import com.jwt.dto.LoginResponseDto;
import com.jwt.entities.User;
import com.jwt.services.AuthService;
import com.jwt.services.JwtService;
import com.jwt.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;

	private final JwtService jwtService;

	private final UserService userService;

	@Override
	public LoginResponseDto login(final LoginDto loginDto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

		User user = (User) authentication.getPrincipal();

		String accessToken = jwtService.generateAccessToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);

		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginResponseDto.setAccessToken(accessToken);
		loginResponseDto.setRefreshToken(refreshToken);
		loginResponseDto.setId(user.getId());
		return loginResponseDto;
	}

	@Override
	public LoginResponseDto refreshToken(final String refreshToken) {
		Long userId = jwtService.getUserIdFromToken(refreshToken);

		User user = userService.getUserById(userId);

		String accessToken = jwtService.generateAccessToken(user);

		return new LoginResponseDto(userId, accessToken, refreshToken);
	}

}
