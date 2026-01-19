package com.example.employeeManagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payrollId;

    private Double basicSalary;

    private Double allowances;

    private Double deductions;

    private Double netSalary;
    //inverse side
    @OneToOne()
    @JsonManagedReference
    private Employee employee;

    public void calculateNetSalary() {
        netSalary = basicSalary+allowances-deductions;
    }



}
