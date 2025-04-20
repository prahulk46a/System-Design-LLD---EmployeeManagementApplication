package com.example.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.EmployeeDTO;
import com.example.demo.entities.Employee;
import com.example.demo.repositories.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
	
	 private EmployeeRepository employeeRepository;
	 @Autowired
	 public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
			this.employeeRepository = employeeRepository;
	 }

	 @Override
	    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
		 	employeeDTO.setId(null); // Ensure ID is not manually set
	        Employee employee = dtoToEntity(employeeDTO);
	        Employee savedEmployee = employeeRepository.save(employee);
	        return entityToDto(savedEmployee);
	    }

	    @Override
	    public List<EmployeeDTO> getAllEmployees() {
	        return employeeRepository.findAll()
	                .stream()
	                .map(this::entityToDto)
	                .collect(Collectors.toList());
	    }

	    @Override
	    public EmployeeDTO getEmployeeById(Long id) {
	        Employee emp = employeeRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
	        return entityToDto(emp);
	    }

	    @Override
	    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
	        Employee existingEmployee = employeeRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

	        System.out.println("UPDATE PAYLOAD: " + employeeDTO);
	        existingEmployee.setFirstName(employeeDTO.getFirstName());
	        existingEmployee.setLastName(employeeDTO.getLastName());
	        existingEmployee.setEmail(employeeDTO.getEmail());
	        existingEmployee.setDepartment(employeeDTO.getDepartment());

	        Employee updatedEmployee = employeeRepository.save(existingEmployee);
	        return entityToDto(updatedEmployee);
	    }

	    @Override
	    public void deleteEmployee(Long id) {
	        employeeRepository.deleteById(id);
	    }

	    private Employee dtoToEntity(EmployeeDTO dto) {
	    	 return Employee.builder()
	                 .firstName(dto.getFirstName())
	                 .lastName(dto.getLastName())
	                 .email(dto.getEmail())
	                 .department(dto.getDepartment())
	                 .build();
	    }

	    private EmployeeDTO entityToDto(Employee emp) {
	        return EmployeeDTO.builder()
	                .id(emp.getEmployeeId())
	                .firstName(emp.getFirstName())
	                .lastName(emp.getLastName())
	                .email(emp.getEmail())
	                .department(emp.getDepartment())
	                .build();
	    }

		

		
	
	
	
}
