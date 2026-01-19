package com.example.employeeManagement.entity;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="attendance_status", nullable = false)
    private String attendanceStatus;
    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

    private Double workingHours;
    private LocalDate date;


}
