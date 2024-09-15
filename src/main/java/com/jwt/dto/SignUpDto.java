package com.jwt.dto;

import java.io.Serializable;
import java.util.Set;

import com.jwt.entities.enums.Role;

import lombok.Data;

@Data
public class SignUpDto implements Serializable {

	private static final long serialVersionUID = -111020523696871315L;

	private String email;

	private String password;

	private String name;

	private Set<Role> roles;
}
