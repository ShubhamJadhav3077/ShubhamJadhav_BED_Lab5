package com.gl.EmployeeManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.gl.EmployeeManagement.entity.Employee;
import com.gl.EmployeeManagement.service.EmployeeServiceImpl;

@Controller
public class EmployeeController {
	@Autowired
	EmployeeServiceImpl employeeService;

	@GetMapping("/employees")
	public String listEmployees(Model model) {
		List<Employee> employeeList = employeeService.getAllEmployees();
		model.addAttribute("employees", employeeList);
		return "employees";
	}

	@GetMapping("/employees/new")
	public String createEmployeeForm(Model model) {
		Employee freshEmployee = new Employee();
		model.addAttribute("employee", freshEmployee);
		return "create-employee";
	}

	@PostMapping("/employees")
	public String saveEmployee(@ModelAttribute("employee") Employee freshEmployee) {
		employeeService.saveEmployee(freshEmployee);
		return "redirect:/employees";
	}

	@GetMapping("/employees/edit/{id}")
	public String editEmployeeForm(@PathVariable int id, Model model) {
		Employee editEmployee = employeeService.getEmployeeById(id);
		model.addAttribute("employee", editEmployee);
		return "edit-employee";
	}

	@PostMapping("/employees/{id}")
	public String updateEmployee(@PathVariable int id, @ModelAttribute("employee") Employee newEmployee) {
		Employee oldEmployee = employeeService.getEmployeeById(id);
		oldEmployee.setFirstName(newEmployee.getFirstName());
		oldEmployee.setLastName(newEmployee.getLastName());
		oldEmployee.setEmail(newEmployee.getEmail());
		employeeService.updateEmployee(oldEmployee);
		return "redirect:/employees";
	}

	@GetMapping("/employees/{id}")
	public String deleteEmployee(@PathVariable int id) {
		employeeService.deleteEmployeeById(id);
		return "redirect:/employees";
	}
}
