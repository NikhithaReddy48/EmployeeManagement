package com.example.employeeManagement.repository;

import com.example.employeeManagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    @Query("SELECT u FROM Employee u WHERE u.empName = :empName")
    Employee findByName(@Param("empName") String empName);

    @Query("Select e from Employee e order by salary desc Limit 1" )
    Employee findByMaxSalary();

    List<Employee> findByDepartment_DepartmentId(Long departmentId);

    @Query("Select e from Employee e order by salary asc Limit 1")
    Employee findByMinSalary();
}
