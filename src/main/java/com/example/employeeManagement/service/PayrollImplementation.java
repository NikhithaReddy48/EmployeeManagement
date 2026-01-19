package com.example.employeeManagement.service;

import com.example.employeeManagement.entity.Employee;
import com.example.employeeManagement.exception.ApiResponse;
import com.example.employeeManagement.dto.PayrollDto;
import com.example.employeeManagement.entity.Payroll;
import com.example.employeeManagement.repository.EmployeeRepository;
import com.example.employeeManagement.repository.PayrollRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PayrollImplementation implements PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Get all payrolls
    @Override
    public ResponseEntity<ApiResponse<List<PayrollDto>>> getALlPayroll() {
        try {
            List<Payroll> payrolls = payrollRepository.findAll();
            if (payrolls.isEmpty()) {
                ApiResponse<List<PayrollDto>> error = new ApiResponse<>(null, "Payroll Not Found");
                error.setSuccess(false);
                error.setErrorCode(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            List<PayrollDto> payrollDtos = payrolls.stream()
                    .map(payroll -> modelMapper.map(payroll, PayrollDto.class))
                    .collect(Collectors.toList());

            ApiResponse<List<PayrollDto>> response = new ApiResponse<>(payrollDtos, "Payrolls fetched successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse<List<PayrollDto>> error = new ApiResponse<>(null, "Internal Server Error");
            error.setSuccess(false);
            error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get payroll by ID
    @Override
    public ResponseEntity<ApiResponse<PayrollDto>> getPayrollById(Long payrollId) {
        try {
            Optional<Payroll> payrollOpt = payrollRepository.findById(payrollId);
            if (payrollOpt.isEmpty()) {
                ApiResponse<PayrollDto> error = new ApiResponse<>(null, "No Payroll found with id " + payrollId);
                error.setSuccess(false);
                error.setErrorCode(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            Payroll payroll = payrollOpt.get();
            PayrollDto payrollDto = modelMapper.map(payroll, PayrollDto.class);
            payrollDto.setEmpId(payroll.getEmployee().getEmpId());

            ApiResponse<PayrollDto> response = new ApiResponse<>(payrollDto, "Payroll fetched successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse<PayrollDto> error = new ApiResponse<>(null, "Internal Server Error");
            error.setSuccess(false);
            error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<ApiResponse<PayrollDto>> createPayroll(PayrollDto payrollDto) {
        try {
            Employee employee = employeeRepository.findById(payrollDto.getEmpId())
                    .orElseThrow(() -> new RuntimeException("Employee not found with id " + payrollDto.getEmpId()));

            Payroll payroll = modelMapper.map(payrollDto, Payroll.class);
            payroll.setEmployee(employee);
            payroll.calculateNetSalary();

            Payroll savedPayroll = payrollRepository.save(payroll);
            PayrollDto responseDto = modelMapper.map(savedPayroll, PayrollDto.class);
            responseDto.setEmpId(employee.getEmpId());

            ApiResponse<PayrollDto> response = new ApiResponse<>(responseDto, "Payroll created successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse<PayrollDto> error = new ApiResponse<>(null, "Internal Server Error");
            error.setSuccess(false);
            error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Double>> getMonthlySalary(Long empId) {
        try {
            Optional<Payroll> optionalPayroll = payrollRepository.findByEmployeeEmpId(empId);
            if (optionalPayroll.isEmpty()) {
                ApiResponse<Double> error = new ApiResponse<>(null, "Payroll not found for employee id " + empId);
                error.setSuccess(false);
                error.setErrorCode(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            Payroll payroll = optionalPayroll.get();
            if (payroll.getNetSalary() == null) {
                payroll.calculateNetSalary();
            }

            int workingDays = attendanceService.getNoOfWorkingAttendanceOfEmp(empId);
            Double monthlySalary = (payroll.getNetSalary() / 24) * workingDays;
            ApiResponse<Double> response = new ApiResponse<>(monthlySalary, "Monthly salary calculated successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse<Double> error = new ApiResponse<>(null, "Internal Server Error");
            error.setSuccess(false);
            error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




@Override
    public PayrollDto createEmployee(PayrollDto payrollDto) {

        Payroll payroll = modelMapper.map(payrollDto, Payroll.class);
        Employee employee = employeeRepository.findById(payrollDto.getEmpId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        payroll.setEmployee(employee);
        payroll.calculateNetSalary();
        Payroll savedPayroll = payrollRepository.save(payroll);
        PayrollDto responseDto = modelMapper.map(savedPayroll, PayrollDto.class);
        responseDto.setEmpId(employee.getEmpId());

        return responseDto;
    }

    }



