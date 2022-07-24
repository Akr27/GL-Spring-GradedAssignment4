package com.greatlearning.spring.employeemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.greatlearning.spring.employeemanagement.entity.Employee;
import com.greatlearning.spring.employeemanagement.repository.EmployeeRepository;
import com.greatlearning.spring.employeemanagement.service.EmployeeService;



@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/api/employees")
	public ResponseEntity<List<Employee>> getAllEmployees(){
		return ResponseEntity.ok().body(employeeService.getAllEmployees());
		
	}
	
	@GetMapping("/api/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable int id){
		return ResponseEntity.ok().body(employeeService.getEmployeeById(id));
		
	}
	
	@PostMapping("/api/employees")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
		return ResponseEntity.ok().body(this.employeeService.addEmployee(employee));
	}
	
	@PutMapping("/api/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable int id,@RequestBody Employee employee){
		employee.setId(id);
		return ResponseEntity.ok().body(this.employeeService.updateEmployee(employee));
	}
	
	@DeleteMapping("/api/employees/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable int id){
		this.employeeService.deleteEmployeeById(id);
		return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.OK);
	}
	
	@GetMapping("/api/employees/sort?order=asc")
	public ResponseEntity<List<Employee>> getEmployeesSortedByFirstNameAsc(String firstName){
		return ResponseEntity.ok().body(employeeService.getEmployeesSortedByFirstNameAsc(firstName));
		
	}
	
	@GetMapping("/api/employees/sort?order=desc")
	public ResponseEntity<List<Employee>> getEmployeesSortedByFirstNameDesc(String firstName){
		return ResponseEntity.ok().body(employeeService.getEmployeesSortedByFirstNameDesc(firstName));
		
	}
	
	@Autowired
	EmployeeRepository empRepo;
	
	@GetMapping("/api/employees/search/{firstName}")
	public ResponseEntity<List<Employee>> getEmployeesByFirstName(@PathVariable String firstName){
		return new ResponseEntity<List<Employee>>(empRepo.findByFirstName(firstName),HttpStatus.OK);
	}
	
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstName", "asc", model);		
	}
	
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		// create model attribute to bind form data
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "new_employee";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		employeeService.saveEmployee(employee);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		Employee employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee", employee);
		return "update_employee";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") long id) {
		this.employeeService.deleteEmployeeById(id);
		return "redirect:/";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Employee> listEmployees = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listEmployees", listEmployees);
		return "index";
	}
}
