package com.jwt.services;

import com.jwt.dto.LoginDto;
import com.jwt.dto.LoginResponseDto;

public interface AuthService {

	LoginResponseDto login(LoginDto loginDto);

	LoginResponseDto refreshToken(String refreshToken);

}
