package com.example.employeeManagement.Mapper;

import com.example.employeeManagement.dto.ProjectDto;
import com.example.employeeManagement.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


 @Mapper(componentModel = "spring")
 public interface ProjectMapper
 {


     @Mapping(source = "department.departmentId", target = "departmentId")

        ProjectDto projectToProjectDTO(Project project);
        Project projectDTOToProject(ProjectDto projectDTO);
 }
