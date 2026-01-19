package com.example.employeeManagement.service;

import com.example.employeeManagement.exception.ApiResponse;
import com.example.employeeManagement.Mapper.ProjectMapper;
import com.example.employeeManagement.dto.ProjectDto;
import com.example.employeeManagement.entity.Department;
import com.example.employeeManagement.entity.Project;
import com.example.employeeManagement.repository.Departmentrepository;
import com.example.employeeManagement.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectImplementation implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private Departmentrepository departmentrepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProjectMapper projectMapper;

    // Get all projects
    @Override
    public ResponseEntity<ApiResponse<List<ProjectDto>>> getAllProjects() {
        try {
            List<Project> projects = projectRepository.findAll();
            if (projects.isEmpty()) {
                ApiResponse<List<ProjectDto>> error = new ApiResponse<>(null, "Projects Not Found");
                error.setSuccess(false);
                error.setErrorCode(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            List<ProjectDto> dto = projects.stream()
                    .map(projectMapper::projectToProjectDTO)
                    .collect(Collectors.toList());

            ApiResponse<List<ProjectDto>> response = new ApiResponse<>(dto, "Projects fetched successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse<List<ProjectDto>> error = new ApiResponse<>(null, "Internal Server Error");
            error.setSuccess(false);
            error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get project by ID
    @Override
    public ResponseEntity<ApiResponse<ProjectDto>> getProjectById(Long projectId) {
        try {
            Optional<Project> project = projectRepository.findById(projectId);
            if (project.isEmpty()) {
                ApiResponse<ProjectDto> error = new ApiResponse<>(null, "Project Not Found By Id");
                error.setSuccess(false);
                error.setErrorCode(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            ProjectDto projectDto = projectMapper.projectToProjectDTO(project.get());
            ApiResponse<ProjectDto> response = new ApiResponse<>(projectDto, "Project fetched successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse<ProjectDto> error = new ApiResponse<>(null, "Internal Server Error");
            error.setSuccess(false);
            error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @Override
    public ResponseEntity<ApiResponse<ProjectDto>> createproject(ProjectDto projectDto) {
        try {
            Project project = projectMapper.projectDTOToProject(projectDto);
            Optional<Department> dep = departmentrepository.findById(projectDto.getDepartmentId());

            if (dep.isEmpty()) {
                ApiResponse<ProjectDto> error = new ApiResponse<>(null, "Department Not Found");
                error.setSuccess(false);
                error.setErrorCode(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            project.setDepartment(dep.get());
            Project savedProject = projectRepository.save(project);
            ProjectDto dto = projectMapper.projectToProjectDTO(savedProject);

            ApiResponse<ProjectDto> response = new ApiResponse<>(dto, "Project created successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse<ProjectDto> error = new ApiResponse<>(null, "Internal Server Error");
            error.setSuccess(false);
            error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<ProjectDto>> getProjectByName(String projectName) {
        try {
            Project project = projectRepository.findByProjectName(projectName);
            if (project == null) {
                ApiResponse<ProjectDto> error = new ApiResponse<>(null, "Project Not Found By Name");
                error.setSuccess(false);
                error.setErrorCode(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            ProjectDto projectDto = projectMapper.projectToProjectDTO(project);
            ApiResponse<ProjectDto> response = new ApiResponse<>(projectDto, "Project fetched successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse<ProjectDto> error = new ApiResponse<>(null, "Internal Server Error");
            error.setSuccess(false);
            error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





@Override
    public Map<String, Long> getProjectCountByDepartment() {

            List<Object[]> result = projectRepository.countProjectsByDepartment();
            Map<String, Long> map = new HashMap<>();
            for (Object[] row : result) {
                String depName = (String) row[0];
                Long count = (Long) row[1];
                map.put(depName, count);
            }
            return map;



    }
}
