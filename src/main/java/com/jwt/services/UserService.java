package com.jwt.services;

import com.jwt.dto.SignUpDto;
import com.jwt.dto.UserDto;
import com.jwt.entities.User;

public interface UserService {

	/**
	 * 
	 * @param signUpDto
	 * @return
	 */
	UserDto signUp(SignUpDto signUpDto);

	/**
	 * 
	 * @param id
	 * @return
	 */
	User getUserById(Long id);

}
