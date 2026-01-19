package com.example.employeeManagement.service;

import com.example.employeeManagement.dto.PayrollDto;
import com.example.employeeManagement.exception.ApiResponse;
import org.springframework.http.ResponseEntity;


public interface PayrollService
{

    ResponseEntity<?> getALlPayroll();

    ResponseEntity<?> getPayrollById(Long id);

    PayrollDto createEmployee(PayrollDto payrollDto);

    ResponseEntity<ApiResponse<PayrollDto>> createPayroll(PayrollDto payrollDto);

    ResponseEntity<ApiResponse<Double>> getMonthlySalary(Long empId);
}
