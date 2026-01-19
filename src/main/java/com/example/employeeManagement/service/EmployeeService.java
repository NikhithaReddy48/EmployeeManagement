package com.example.employeeManagement.service;

import com.example.employeeManagement.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface EmployeeService {
     ResponseEntity<?> getAllEmployee();
   ResponseEntity<?> createEmployee(EmployeeDto employeedto);

     ResponseEntity<?> getById(Long id);

    ResponseEntity<?>createEmployees(List<EmployeeDto> employeedtos);

    ResponseEntity<?> getByName(String name);
    EmployeeDto findByMaxSalary();

    EmployeeDto findByMinSalary();
}
