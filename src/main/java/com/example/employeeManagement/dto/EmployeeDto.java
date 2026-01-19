package com.example.employeeManagement.dto;




import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Long empId;
    private String empName;
    private String email;
    private Long departmentId;
    private List<Long> projectIds;


}
