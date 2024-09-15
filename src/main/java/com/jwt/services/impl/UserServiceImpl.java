package com.jwt.services.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.dto.SignUpDto;
import com.jwt.dto.UserDto;
import com.jwt.entities.User;
import com.jwt.repositories.UserRepository;
import com.jwt.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

	private final UserRepository userRepository;

	private final ModelMapper modelMapper;

	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User with email " + username + "not found"));
	}

	@Override
	public User getUserById(final Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User with id " + id + "not found"));
	}

	@Override
	public UserDto signUp(final SignUpDto signUpDto) {

		Optional<User> userOptional = userRepository.findByEmail(signUpDto.getEmail());

		if (userOptional.isPresent()) {
			throw new BadCredentialsException("User with email " + signUpDto.getEmail() + " already exists");
		}

		User user = modelMapper.map(signUpDto, User.class);
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		User save = userRepository.save(user);
		save.setPassword(signUpDto.getPassword());

		return modelMapper.map(save, UserDto.class);
	}

}
