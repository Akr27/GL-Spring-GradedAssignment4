package com.greatlearning.spring.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greatlearning.spring.employeemanagement.entity.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
}
