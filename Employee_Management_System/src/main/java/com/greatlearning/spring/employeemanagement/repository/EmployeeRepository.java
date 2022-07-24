package com.greatlearning.spring.employeemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greatlearning.spring.employeemanagement.entity.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	public List<Employee> findByFirstName(String firstName);
	public Employee findByEmail(String email);

}
