package com.example.employeeManagement.controller;

import com.example.employeeManagement.dto.ProjectDto;
import com.example.employeeManagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Project")

public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @GetMapping
    public ResponseEntity<?> getAllProjects()
    {
        return projectService.getAllProjects();
    }
    @GetMapping("/id/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable Long projectId)
    {
        return projectService.getProjectById(projectId);
    }
    @GetMapping("/name/{projectName}")
    public ResponseEntity<?> getProjectByName(@PathVariable String projectName)
    {
        return ResponseEntity.ok(projectService.getProjectByName(projectName));
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectDto projectDto)
    {
        return projectService.createproject(projectDto);
    }
    @GetMapping("/countProjectsByDepartment")
    public ResponseEntity<Map<String,Long>> getProjectCountByDepartment()
    {
        Map<String,Long> result =projectService.getProjectCountByDepartment();
        return ResponseEntity.ok(result);
    }

}
