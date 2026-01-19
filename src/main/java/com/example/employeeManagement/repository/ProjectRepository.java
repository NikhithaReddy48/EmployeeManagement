package com.example.employeeManagement.repository;

import com.example.employeeManagement.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("SELECT p.department.depName, COUNT(p) FROM Project p GROUP BY p.department.depName")
    List<Object[]> countProjectsByDepartment();

    Project findByProjectName(String projectName);
}
