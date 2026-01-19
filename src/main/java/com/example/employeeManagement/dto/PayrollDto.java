package com.example.employeeManagement.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PayrollDto {
    private Long payrollId;
    private Double basicSalary;
    private Double allowances;
    private Double deductions;
    private Double netSalary;
    private Long empId ;
}
