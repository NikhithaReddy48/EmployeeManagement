package com.example.employeeManagement.controller;

import com.example.employeeManagement.dto.PayrollDto;
import com.example.employeeManagement.exception.ApiResponse;
import com.example.employeeManagement.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/payroll")

public class PayrollController {
    @Autowired
    private PayrollService  payrollService;
    @GetMapping
    public ResponseEntity<?> getAllPayroll()
    {
        return payrollService.getALlPayroll();

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getPayrollById(@PathVariable Long payrollId )
    {
        return ResponseEntity.ok(payrollService.getPayrollById(payrollId));
    }

    @PostMapping
    public ResponseEntity<PayrollDto> createPayroll(@RequestBody PayrollDto payrollDto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(payrollService.createEmployee(payrollDto));

    }
    @GetMapping("/employee/{empId}")
    public ResponseEntity<ApiResponse<Double>> getMonthlySalary(@PathVariable Long empId)
    {
        return payrollService.getMonthlySalary(empId);
    }

}

