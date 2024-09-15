package com.jwt.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserDto implements Serializable {

	private static final long serialVersionUID = 7104533370792582271L;
	private Long id;
	private String email;
	private String name;
}
