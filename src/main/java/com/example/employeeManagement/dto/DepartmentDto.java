package com.example.employeeManagement.dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor


public class DepartmentDto
{
private Long departmentId;
private String depName;
private List<Long> employeeIds;
private List<Long> projectIds;

}
