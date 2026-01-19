package com.example.employeeManagement.Mapper;

import com.example.employeeManagement.dto.AttendanceDto;
import com.example.employeeManagement.entity.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface AttendanceMapper
{
    @Mapping(target = "empId", source = "employee.empId")
    AttendanceDto attendanceToAttendanceDto(Attendance attendance);
    @Mapping(target = "employee", ignore = true)
    Attendance attendanceDtoToAttendance(AttendanceDto attendanceDto);
}
