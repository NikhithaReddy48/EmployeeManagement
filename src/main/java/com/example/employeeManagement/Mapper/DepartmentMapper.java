package com.example.employeeManagement.Mapper;

import com.example.employeeManagement.dto.DepartmentDto;
import com.example.employeeManagement.entity.Department;
import com.example.employeeManagement.entity.Employee;
import com.example.employeeManagement.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;
import java.util.stream.Collectors;
@Mapper(componentModel = "spring")
public interface DepartmentMapper {


    @Mapping(source = "employees", target = "employeeIds", qualifiedByName = "mapEmployeeIds")
    @Mapping(source = "projects", target = "projectIds", qualifiedByName = "mapProjectIds")
    DepartmentDto departmentToDepartmentDto(Department department);

    @Mapping(target = "employees", source = "employeeIds", qualifiedByName = "mapIdsToEmployees")
    @Mapping(target = "projects", source = "projectIds", qualifiedByName = "mapIdsToProjects")
    Department departmentDtoToDepartment(DepartmentDto departmentDto);

    @Named("mapEmployeeIds")
    default List<Long> mapEmployeeIds(List<Employee> employees) {
        if (employees == null) return null;
        return employees.stream()
                .map(Employee::getEmpId)
                .collect(Collectors.toList());
    }

    @Named("mapProjectIds")
    default List<Long> mapProjectIds(List<Project> projects) {
        if (projects == null) return null;
        return projects.stream()
                .map(Project::getProjectId)
                .collect(Collectors.toList());
    }

    @Named("mapIdsToEmployees")
    default List<Employee> mapIdsToEmployees(List<Long> empIds) {
        if (empIds == null) return null;
        return empIds.stream()
                .map(empId -> {
                    Employee e = new Employee();
                    e.setEmpId(empId);
                    return e;
                })
                .collect(Collectors.toList());
    }
    @Named("mapIdsToProjects")
    default List<Project> mapIdsToProjects(List<Long> ids) {
        if (ids == null) return null;
        return ids.stream()
                .map(id -> {
                    Project p = new Project();
                    p.setProjectId(id);
                    return p;
                })
                .collect(Collectors.toList());
    }
}
