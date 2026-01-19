package com.example.employeeManagement.controller;

import com.example.employeeManagement.dto.AttendanceDto;

import com.example.employeeManagement.exception.ApiResponse;
import com.example.employeeManagement.service.AttendanceService;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RequestMapping("/attendance")
@RestController
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;


    @PostMapping("/{empId}")
    public ResponseEntity<ApiResponse<AttendanceDto>> markAttendance(@PathVariable Long empId,
                                                      @RequestBody AttendanceDto attendanceDto) {



        return attendanceService.markAttendance(empId, attendanceDto);


    }

    @GetMapping("/employee/{empId}")
    public ResponseEntity<ApiResponse<List<AttendanceDto>>>getAttendanceByEmpId(@PathVariable Long empId) {
        return attendanceService.getAttendanceByEmpId(empId);

    }

    @GetMapping("/date/{date}")
    public ResponseEntity<ApiResponse<List<AttendanceDto>>> getAttendanceOfDate(@PathVariable LocalDate date) {
        return attendanceService.getAttendanceOfDate(date);

    }

    @GetMapping("/{date}/{attendanceStatus}")
    public ResponseEntity<?> getAttendanceStatusOnDate(@PathVariable LocalDate date, @PathVariable String attendanceStatus) {
        return attendanceService.getAttendanceStatusOfDate(date, attendanceStatus);

    }

    @GetMapping("/employee/{empId}/{date}")
    public ResponseEntity<?> getEmployeeAttendanceOnDate(@PathVariable Long empId, @PathVariable LocalDate date) {
       return  attendanceService.getEmployeeAttendanceOnDate(empId, date);

    }

    @GetMapping("/count/{empId}")
    public ResponseEntity<Integer> countWorkingAttendanceOfEmployee(@PathVariable Long empId)
    {
       Integer WorkingDays = attendanceService.getNoOfWorkingAttendanceOfEmp(empId);
        return ResponseEntity.ok(WorkingDays);
    }
}
