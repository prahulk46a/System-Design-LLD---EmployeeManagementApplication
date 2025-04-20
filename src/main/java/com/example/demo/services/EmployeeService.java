package com.example.demo.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.dtos.EmployeeDTO;
public interface EmployeeService {
	EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO getEmployeeById(Long id);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);
    void deleteEmployee(Long id);
    Page<EmployeeDTO> getAllEmployeesPaginated(int page, int size, String sortBy, String direction);
}