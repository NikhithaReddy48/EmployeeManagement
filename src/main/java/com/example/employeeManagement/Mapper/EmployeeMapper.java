package com.example.employeeManagement.Mapper;
import com.example.employeeManagement.dto.EmployeeDto;
import com.example.employeeManagement.entity.Employee;
import com.example.employeeManagement.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(source = "department.departmentId", target = "departmentId")
  //  @Mapping(source = "payroll.payrollId", target = "payrollId")
    @Mapping(source = "projects", target = "projectIds", qualifiedByName = "mapProjectIds")
    EmployeeDto employeeToEmployeeDto(Employee employee);

    @Mapping(target = "department", ignore = true)
  // @Mapping(target = "payroll", ignore = true)
    @Mapping(target = "projects", ignore = true)
    Employee employeeDtoToEmployee(EmployeeDto employeeDto);

    @Named("mapProjectIds")
    default List<Long> mapProjectIds(List<Project> projects) {
        if (projects == null) return null;
        return projects.stream()
                .map(Project::getProjectId)
                .collect(Collectors.toList());
    }
}
