package com.greatlearning.spring.employeemanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greatlearning.spring.employeemanagement.entity.Employee;
import com.greatlearning.spring.employeemanagement.exception.ResourceNotFoundException;
import com.greatlearning.spring.employeemanagement.repository.EmployeeRepository;



@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public Employee addEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public void saveEmployee(Employee employee) {
		this.employeeRepository.save(employee);
	}
	
	@Override
	public Employee updateEmployee(Employee employee) {
		
		Optional<Employee> employeeDb = this.employeeRepository.findById(employee.getId());
		
		if(employeeDb.isPresent()) {
			Employee employeeUpdate = employeeDb.get();
			employeeUpdate.setId(employee.getId());
			employeeUpdate.setFirstName(employee.getFirstName());
			employeeUpdate.setLastName(employee.getLastName());
			employeeUpdate.setEmail(employee.getEmail());
			employeeRepository.save(employeeUpdate);
			return employeeUpdate;
		}
		else {
			throw new ResourceNotFoundException("Record not found for id: "+employee.getId());
		}
		
	}

	@Override
	public Employee getEmployeeById(long id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		Employee employee = null;
		if (optional.isPresent()) {
			employee = optional.get();
		} else {
			throw new RuntimeException(" Employee not found for id :: " + id);
		}
		return employee;
	}

	@Override
	public void deleteEmployeeById(long id) {
		this.employeeRepository.deleteById(id);
	}

	@Override
	public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.employeeRepository.findAll(pageable);
	}
	
	@Override
	public List<Employee> getEmployeesSortedByFirstNameAsc(String firstName){
		return employeeRepository.findAll(Sort.by(Sort.Direction.ASC,firstName));
	}
	
	@Override
	public List<Employee> getEmployeesSortedByFirstNameDesc(String firstName){
		return employeeRepository.findAll(Sort.by(Sort.Direction.DESC,firstName));
	}
}
