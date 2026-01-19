package com.example.employeeManagement.dto;




import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class AttendanceDto {
    private Long id;
    private String attendanceStatus;
    private Double workingHours;
    private Long empId;
    private LocalDate date;



}
