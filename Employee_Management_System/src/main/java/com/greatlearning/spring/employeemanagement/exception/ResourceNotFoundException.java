package com.greatlearning.spring.employeemanagement.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus
public class ResourceNotFoundException extends RuntimeException{
	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	public ResourceNotFoundException(String message,Throwable throwable) {
		super(message,throwable);
	}
	

}
