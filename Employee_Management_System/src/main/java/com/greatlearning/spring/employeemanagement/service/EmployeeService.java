package com.greatlearning.spring.employeemanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.greatlearning.spring.employeemanagement.entity.Employee;



public interface EmployeeService {
	Employee addEmployee(Employee employee);
	Employee updateEmployee(Employee employee);
	List<Employee> getAllEmployees();
	void saveEmployee(Employee employee);
	Employee getEmployeeById(long id);
	void deleteEmployeeById(long id);
	Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
	List<Employee> getEmployeesSortedByFirstNameAsc(String firstName);
	List<Employee> getEmployeesSortedByFirstNameDesc(String firstName);
}
