package com.example.employeeManagement.repository;

import com.example.employeeManagement.entity.Attendance;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance ,Long> {
    Optional<Attendance> findByEmployeeEmpIdAndDate(Long empId, LocalDate date);



    List<Attendance> findALlByEmployeeEmpId(Long empId);

    List<Attendance> findByDate(LocalDate date);



    List<Attendance> findAllByDateAndAttendanceStatus(LocalDate date, String attendanceStatus);

    Integer countByEmployeeEmpIdAndAttendanceStatusIn(Long empId, List<String> status);
}
