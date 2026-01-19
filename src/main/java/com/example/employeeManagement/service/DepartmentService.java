package com.example.employeeManagement.service;

import com.example.employeeManagement.dto.DepartmentDto;
import org.springframework.http.ResponseEntity;



public interface DepartmentService {
    ResponseEntity<?> getAllDep();

    DepartmentDto creatDep(DepartmentDto departmentDto);
    ResponseEntity<?> getDepBYid(Long id);

    ResponseEntity<?> getDepByName(String depName);

   ResponseEntity<?>getAllEmployeeByDep(Long departmentId);
}
