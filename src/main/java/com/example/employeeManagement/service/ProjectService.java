package com.example.employeeManagement.service;

import com.example.employeeManagement.dto.PayrollDto;
import com.example.employeeManagement.dto.ProjectDto;
import com.example.employeeManagement.exception.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;


public interface ProjectService {

    ResponseEntity<ApiResponse<List<ProjectDto>>> getAllProjects();

    ResponseEntity<ApiResponse<ProjectDto>> getProjectById(Long id);

    ResponseEntity<ApiResponse<ProjectDto>> createproject(ProjectDto projectDto);

    ResponseEntity<ApiResponse<ProjectDto>> getProjectByName(String projectName);

    Map<String, Long> getProjectCountByDepartment();
}
