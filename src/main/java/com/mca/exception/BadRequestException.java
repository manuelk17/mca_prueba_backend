package com.mca.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2449363280992188470L;

	public BadRequestException(String message) {
		super(message);
	}
}
