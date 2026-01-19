package com.example.employeeManagement.service;

import com.example.employeeManagement.Mapper.EmployeeMapper;
import com.example.employeeManagement.dto.EmployeeDto;
import com.example.employeeManagement.entity.Department;
import com.example.employeeManagement.entity.Employee;
import com.example.employeeManagement.entity.Project;
import com.example.employeeManagement.exception.ApiResponse;
import com.example.employeeManagement.repository.Departmentrepository;
import com.example.employeeManagement.repository.EmployeeRepository;
import com.example.employeeManagement.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeImplementation implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private Departmentrepository departmentrepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> getAllEmployee() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            if (employees.isEmpty()) {
                ApiResponse<List<EmployeeDto>> error = new ApiResponse<>(null, "Employees Not Found");
                error.setSuccess(false);
                error.setErrorCode(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            List<EmployeeDto> employeeDtoList = employees.stream()
                    .map(employeeMapper::employeeToEmployeeDto)
                    .collect(Collectors.toList());

            ApiResponse<List<EmployeeDto>> response = new ApiResponse<>(employeeDtoList, "Employees fetched successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse<List<EmployeeDto>> error = new ApiResponse<>(null, "Internal Server Error");
            error.setSuccess(false);
            error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<ApiResponse<EmployeeDto>> getById(Long empId) {
        try {
            Optional<Employee> employeeOpt = employeeRepository.findById(empId);
            if (employeeOpt.isEmpty()) {
                ApiResponse<EmployeeDto> error = new ApiResponse<>(null, "Employee Not Found");
                error.setSuccess(false);
                error.setErrorCode(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            EmployeeDto dto = employeeMapper.employeeToEmployeeDto(employeeOpt.get());
            ApiResponse<EmployeeDto> response = new ApiResponse<>(dto, "Employee fetched successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse<EmployeeDto> error = new ApiResponse<>(null, "Internal Server Error");
            error.setSuccess(false);
            error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<ApiResponse<EmployeeDto>> getByName(String empName) {
        try {
            Employee employee = employeeRepository.findByName(empName);
            if (employee == null) {
                ApiResponse<EmployeeDto> error = new ApiResponse<>(null, "Employee Not Found");
                error.setSuccess(false);
                error.setErrorCode(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            EmployeeDto dto = employeeMapper.employeeToEmployeeDto(employee);
            ApiResponse<EmployeeDto> response = new ApiResponse<>(dto, "Employee fetched successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse<EmployeeDto> error = new ApiResponse<>(null, "Internal Server Error");
            error.setSuccess(false);
            error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<ApiResponse<EmployeeDto>> createEmployee(EmployeeDto employeedto) {
        try {
            if (employeedto.getDepartmentId() == null) {
                ApiResponse<EmployeeDto> error = new ApiResponse<>(null, "Department ID cannot be null");
                error.setSuccess(false);
                error.setErrorCode(HttpStatus.BAD_REQUEST.value());
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }

            Optional<Department> depOpt = departmentrepository.findById(employeedto.getDepartmentId());
            if (depOpt.isEmpty()) {
                ApiResponse<EmployeeDto> error = new ApiResponse<>(null, "Department Not Found");
                error.setSuccess(false);
                error.setErrorCode(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            Employee employee = modelMapper.map(employeedto, Employee.class);
            employee.setDepartment(depOpt.get());

            List<Project> projects = new ArrayList<>();
            if (employeedto.getProjectIds() != null && !employeedto.getProjectIds().isEmpty()) {
                projects = projectRepository.findAllById(employeedto.getProjectIds());
            }
            if (projects.isEmpty()) {
                ApiResponse<EmployeeDto> error = new ApiResponse<>(null, "Projects Not Found");
                error.setSuccess(false);
                error.setErrorCode(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            employee.setProjects(projects);

            Employee savedEmployee = employeeRepository.save(employee);

            EmployeeDto responseDto = modelMapper.map(savedEmployee, EmployeeDto.class);
            responseDto.setProjectIds(
                    savedEmployee.getProjects().stream().map(Project::getProjectId).toList()
            );

            ApiResponse<EmployeeDto> response = new ApiResponse<>(responseDto, "Employee created successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse<EmployeeDto> error = new ApiResponse<>(null, "Internal Server Error");
            error.setSuccess(false);
            error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public EmployeeDto findByMaxSalary() {
        Employee employee = employeeRepository.findByMaxSalary();
        return employeeMapper.employeeToEmployeeDto(employee);
    }


    @Override
    public EmployeeDto findByMinSalary() {
        Employee employee = employeeRepository.findByMinSalary();
        return employeeMapper.employeeToEmployeeDto(employee);
    }


    @Override
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> createEmployees(List<EmployeeDto> employeedtos) {
        return null;
    }
}
