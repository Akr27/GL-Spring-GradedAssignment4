package com.greatlearning.spring.employeemanagement.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.greatlearning.spring.employeemanagement.dto.UserRegistrationDto;
import com.greatlearning.spring.employeemanagement.entity.User;



public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
