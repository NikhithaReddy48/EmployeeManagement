package com.example.employeeManagement.controller;

import com.example.employeeManagement.dto.DepartmentDto;

import com.example.employeeManagement.exception.ApiResponse;
import com.example.employeeManagement.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;


    @GetMapping
        public ResponseEntity<?>getAllDepartment ()
        {


            return departmentService.getAllDep();


        }

    @GetMapping("/id/{departmentId}")
    public ResponseEntity<?> getDepById(@PathVariable Long departmentId)
    {

            return  departmentService.getDepBYid(departmentId);



    }
    @GetMapping("/name/{depName}")
    public ResponseEntity<?> getDepByName(@PathVariable String depName)
    {

           return  departmentService.getDepByName(depName);

    }

    @PostMapping
    public ResponseEntity<DepartmentDto> createDep(@RequestBody DepartmentDto departmentDto) {

            DepartmentDto departmentDto1 =departmentService.creatDep(departmentDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(departmentDto1);


    }
    @GetMapping("/{departmentId}/employee")
    public ResponseEntity<?> getALlEmployeeByDep(@PathVariable Long departmentId)
    {
        return departmentService.getAllEmployeeByDep(departmentId);
    }

}
