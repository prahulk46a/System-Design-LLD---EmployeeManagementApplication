package com.example.demo.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dtos.EmployeeDTO;
import com.example.demo.entities.Employee;
import com.example.demo.services.EmployeeService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")

public class EmployeeController {
	
	private final EmployeeService employeeService;
	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/employees")
	public ResponseEntity<List<EmployeeDTO>> getAllEmployee(){
		return ResponseEntity.ok(employeeService.getAllEmployees());
	}
	
	
	
	@PostMapping("/employees/create")
	public ResponseEntity<EmployeeDTO>create(@Valid @RequestBody EmployeeDTO dto){
		return ResponseEntity.ok(employeeService.createEmployee(dto));
	}
	
	@GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable Long id, @Valid @RequestBody EmployeeDTO dto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
	
}
