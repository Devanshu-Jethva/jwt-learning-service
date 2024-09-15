package com.jwt.controllers;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jwt.dto.LoginDto;
import com.jwt.dto.LoginResponseDto;
import com.jwt.dto.SignUpDto;
import com.jwt.dto.UserDto;
import com.jwt.services.AuthService;
import com.jwt.services.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<UserDto> signUp(@RequestBody final SignUpDto signUpDto) {
		UserDto userDto = userService.signUp(signUpDto);
		return ResponseEntity.ok(userDto);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody final LoginDto loginDto,
			final HttpServletResponse httpServletResponse) {
		LoginResponseDto loginResponseDto = authService.login(loginDto);

		Cookie cookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());
		cookie.setHttpOnly(true);
		httpServletResponse.addCookie(cookie);

		return ResponseEntity.ok(loginResponseDto);
	}

	@GetMapping("/refresh")
	public ResponseEntity<LoginResponseDto> refreshToken(final HttpServletRequest httpServletRequest) {
		Cookie[] cookies = httpServletRequest.getCookies();

		String refreshToken = Arrays.stream(cookies).filter(cookie -> "refreshToken".equals(cookie.getName()))
				.map(Cookie::getValue).findFirst()
				.orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside cookie"));

		LoginResponseDto loginResponseDto = authService.refreshToken(refreshToken);

		return ResponseEntity.ok(loginResponseDto);
	}

}
