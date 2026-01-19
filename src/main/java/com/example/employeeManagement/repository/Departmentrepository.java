package com.example.employeeManagement.repository;

import com.example.employeeManagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface Departmentrepository extends JpaRepository<Department ,Long>
{
    @Query("select u from Department u where u.depName=:depName")
    Department findByDepName(String depName);

}
