package com.example.employeeManagement.service;


import com.example.employeeManagement.exception.ApiResponse;
import com.example.employeeManagement.Mapper.DepartmentMapper;
import com.example.employeeManagement.dto.DepartmentDto;
import com.example.employeeManagement.dto.EmployeeDto;
import com.example.employeeManagement.entity.Department;
import com.example.employeeManagement.entity.Employee;
import com.example.employeeManagement.repository.Departmentrepository;
import com.example.employeeManagement.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service

public class DepartmentImplementation implements DepartmentService{
    @Autowired
    private Departmentrepository departmentrepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public ResponseEntity<ApiResponse<List<DepartmentDto>>> getAllDep() {
        try {
            List<Department> departments = departmentrepository.findAll();

            if (departments.isEmpty()) {
                ApiResponse<List<DepartmentDto>> error = new ApiResponse<>(
                        null,
                        "No Department Found"
                );
                error.setSuccess(false);
                error.setErrorCode(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            List<DepartmentDto> departmentDtos = departments.stream()
                    .map(departmentMapper::departmentToDepartmentDto)
                    .collect(Collectors.toList());

            ApiResponse<List<DepartmentDto>> response = new ApiResponse<>(
                    departmentDtos,
                    "Departments fetched successfully"
            );
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse<List<DepartmentDto>> error = new ApiResponse<>(
                    null,
                    "Internal Server Error"
            );
            error.setSuccess(false);
            error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> getAllEmployeeByDep(Long departmentId) {
        try {
            if (!departmentrepository.existsById(departmentId)) {
                ApiResponse<List<EmployeeDto>> error = new ApiResponse<>(
                        null,
                        "No Department Found"
                );
                error.setSuccess(false);
                error.setErrorCode(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            List<Employee> employees = employeeRepository.findByDepartment_DepartmentId(departmentId);

            if (employees.isEmpty()) {
                ApiResponse<List<EmployeeDto>> error = new ApiResponse<>(
                        null,
                        "No Employee Found in the given Department"
                );
                error.setSuccess(false);
                error.setErrorCode(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            List<EmployeeDto> employeeDtoList = employees.stream()
                    .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                    .collect(Collectors.toList());


            ApiResponse<List<EmployeeDto>> response = new ApiResponse<>(
                    employeeDtoList,
                    "Employees fetched successfully"
            );
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse<List<EmployeeDto>> error = new ApiResponse<>(
                    null,
                    "Internal Server Error"
            );
            error.setSuccess(false);
            error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public DepartmentDto creatDep(DepartmentDto departmentDto) {


            Department department = departmentMapper.departmentDtoToDepartment(departmentDto);
            Department department1 = departmentrepository.save(department);
            return departmentMapper.departmentToDepartmentDto(department1);
        }




    @Override
    public ResponseEntity<ApiResponse<DepartmentDto>> getDepBYid(Long id) {
        try {
            Optional<Department> departmentOpt = departmentrepository.findById(id);

            if (departmentOpt.isEmpty()) {
                ApiResponse<DepartmentDto> error = new ApiResponse<>(
                        null,
                        "Department Not Found"
                );
                error.setSuccess(false);
                error.setErrorCode(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            DepartmentDto departmentDto = departmentMapper.departmentToDepartmentDto(departmentOpt.get());
            ApiResponse<DepartmentDto> response = new ApiResponse<>(departmentDto, "Department fetched successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse<DepartmentDto> error = new ApiResponse<>(
                    null,
                    "Internal Server Error"
            );
            error.setSuccess(false);
            error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Override
    public ResponseEntity<ApiResponse<DepartmentDto>> getDepByName(String depName) {
        try {
            Department department = departmentrepository.findByDepName(depName);

            if (department == null) {
                ApiResponse<DepartmentDto> error = new ApiResponse<>(
                        null,
                        "Department Not Found"
                );
                error.setSuccess(false);
                error.setErrorCode(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            DepartmentDto departmentDto = departmentMapper.departmentToDepartmentDto(department);
            ApiResponse<DepartmentDto> response = new ApiResponse<>(departmentDto, "Department fetched successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse<DepartmentDto> error = new ApiResponse<>(
                    null,
                    "Internal Server Error"
            );
            error.setSuccess(false);
            error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}





