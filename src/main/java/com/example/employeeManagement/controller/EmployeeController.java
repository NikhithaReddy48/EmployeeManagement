package com.example.employeeManagement.controller;

import com.example.employeeManagement.dto.EmployeeDto;
import com.example.employeeManagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/employee")

public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> getEmployee()
    {

            return employeeService.getAllEmployee();
    }

    @GetMapping("/{empId}")
    public ResponseEntity<?> getEmpById(@PathVariable Long empId)
    {
              return employeeService.getById(empId);



    }
    @GetMapping("/name/{empName}")
    public ResponseEntity<?> getEmpByName(@PathVariable String empName)
    {

            return employeeService.getByName(empName);

    }
    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDto employeedto)
    {

            return employeeService.createEmployee(employeedto);

    }
    @GetMapping("/maxSalary")
    public ResponseEntity<EmployeeDto> getMaxSalaryEmp()
    {
        EmployeeDto employeeDto = employeeService.findByMaxSalary();
        return ResponseEntity.ok(employeeDto);
    }
    @GetMapping("/minSalary")
    public ResponseEntity<EmployeeDto> getMinSalaryEmp()
    {
        EmployeeDto employeeDto = employeeService.findByMinSalary();
        return ResponseEntity.ok(employeeDto);
    }


}
