package com.jwt.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7920474450304146538L;

	public ResourceNotFoundException(final String message) {
		super(message);
	}
}
