package com.example.employeeManagement.dto;

import com.example.employeeManagement.entity.Project.Status;
import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    private Long projectId;
    private String projectName;
    private Status status;
    private Long departmentId;


}
