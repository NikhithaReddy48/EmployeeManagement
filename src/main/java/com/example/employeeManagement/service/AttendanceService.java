package com.example.employeeManagement.service;
import com.example.employeeManagement.exception.ApiResponse;
import com.example.employeeManagement.Mapper.AttendanceMapper;
import com.example.employeeManagement.dto.AttendanceDto;
import com.example.employeeManagement.entity.Attendance;
import com.example.employeeManagement.entity.Employee;
import com.example.employeeManagement.repository.AttendanceRepository;
import com.example.employeeManagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.time.DayOfWeek;
@Service

public class AttendanceService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private AttendanceMapper attendanceMapper;

    public ResponseEntity<ApiResponse<AttendanceDto>> markAttendance(Long empId, AttendanceDto attendanceDto) {
        try {
            Optional<Employee> employee = employeeRepository.findById(empId);
            if (employee.isEmpty()) {
                ApiResponse<AttendanceDto> error = new ApiResponse<>(
                        "Employee not found with id " + empId,
                        HttpStatus.NOT_FOUND.value()
                );
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
            Employee employee1 = employee.get();


            DayOfWeek day = attendanceDto.getDate().getDayOfWeek();
            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                ApiResponse<AttendanceDto> error = new ApiResponse<AttendanceDto>(
                        "Cannot mark attendance on weekend",
                        HttpStatus.BAD_REQUEST.value()
                );
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }


            Optional<Attendance> existing = attendanceRepository
                    .findByEmployeeEmpIdAndDate(empId, attendanceDto.getDate());
            if (existing.isPresent()) {
                ApiResponse<AttendanceDto> error = new ApiResponse<AttendanceDto>(
                        "Attendance already marked",
                        HttpStatus.BAD_REQUEST.value()
                );
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }


            Attendance attendance = attendanceMapper.attendanceDtoToAttendance(attendanceDto);
            attendance.setEmployee(employee1);
            Attendance saved = attendanceRepository.save(attendance);

            AttendanceDto responseDto = attendanceMapper.attendanceToAttendanceDto(saved);
            ApiResponse<AttendanceDto> response = new ApiResponse<>(responseDto,"Attendance marked successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            ApiResponse<AttendanceDto> error = new ApiResponse<AttendanceDto>(
                    "Internal Server Error",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse<List<AttendanceDto>>> getAttendanceByEmpId(Long empId) {
        try {

            if (!employeeRepository.existsById(empId)) {
                ApiResponse<List<AttendanceDto>> error = new ApiResponse<>(
                        "Employee Not found " + empId,
                        HttpStatus.NOT_FOUND.value()
                );
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            List<Attendance> attendances = attendanceRepository.findALlByEmployeeEmpId(empId);
            if (attendances.isEmpty()) {
                ApiResponse<List<AttendanceDto>> error = new ApiResponse<>(
                        "No attendance marked for This Employee",
                        HttpStatus.BAD_REQUEST.value()
                );
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }

            List<AttendanceDto> attendanceDtos = attendances.stream()
                    .map(attendanceMapper::attendanceToAttendanceDto)
                    .collect(Collectors.toList());

            ApiResponse<List<AttendanceDto>> response = new ApiResponse<>(
                    attendanceDtos,
                    "Attendance fetched successfully"
            );
            return ResponseEntity.ok(response);

        } catch(Exception e) {
            ApiResponse<List<AttendanceDto>> error = new ApiResponse<>(
                    "Internal Server Error",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<ApiResponse<List<AttendanceDto>>> getAttendanceOfDate(LocalDate date) {
        try {

            List<Attendance> attendances = attendanceRepository.findByDate(date);
            if (attendances.isEmpty()) {
                ApiResponse<List<AttendanceDto>> apiResponse = new ApiResponse<>(
                        null,
                        "Attendance Not found on " + date
                );
                apiResponse.setSuccess(false);
                apiResponse.setErrorCode(HttpStatus.NO_CONTENT.value());
                return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
            }

            List<AttendanceDto> attendanceDtos = attendances.stream()
                    .map(attendanceMapper::attendanceToAttendanceDto)
                    .collect(Collectors.toList());

            ApiResponse<List<AttendanceDto>> response = new ApiResponse<>(
                    attendanceDtos,
                    "Attendance fetched successfully"
            );
            return ResponseEntity.ok(response);

        } catch(Exception e) {
            ApiResponse<List<AttendanceDto>> error = new ApiResponse<>(
                    null,
                    "Internal Server Error"
            );
            error.setSuccess(false);
            error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAttendanceStatusOfDate(LocalDate date, String attendanceStatus) {

        try {

            List<Attendance> attendances = attendanceRepository.findAllByDateAndAttendanceStatus(date, attendanceStatus);
            if (attendances.isEmpty()) {
               // throw new BusinessException("Did not found attendance status on given date");
                ApiResponse<AttendanceDto> apiResponse = new ApiResponse<AttendanceDto>(
                        "Did not found attendance status on given date",
                        HttpStatus.NOT_FOUND.value()
                );
                return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);

            }
            List<AttendanceDto> attendanceDto = attendances.stream()
                    .map(attendance -> attendanceMapper.attendanceToAttendanceDto(attendance))
                    .collect(Collectors.toList());
            ApiResponse<List<AttendanceDto>> response = new ApiResponse<>(attendanceDto,"Attendance fetched successfully");
            return ResponseEntity.ok(response);
          //  return ResponseEntity.ok(attendanceDto);
        }
        catch(Exception e) {
            ApiResponse<AttendanceDto> error = new ApiResponse<AttendanceDto>(
                    "Internal Server Error",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    public ResponseEntity<?>getEmployeeAttendanceOnDate(Long empId, LocalDate date) {
        try {

            Optional<Attendance> attendances = attendanceRepository.findByEmployeeEmpIdAndDate(empId, date);
            if (attendances.isEmpty()) {
                ApiResponse<AttendanceDto> apiResponse = new ApiResponse<AttendanceDto>(
                        "Attendance Not found of Employee" + empId + "on given date" + date,
                        HttpStatus.NOT_FOUND.value()

                );
                return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
            }
             AttendanceDto attendanceDto =attendanceMapper.attendanceToAttendanceDto(attendances.get());
            ApiResponse<AttendanceDto> response = new ApiResponse<>(attendanceDto,"Attendance Fetched successfully");
            return ResponseEntity.ok(response);

         //   return ResponseEntity.ok(attendanceDto);
        }
        catch(Exception e)
        { ApiResponse<AttendanceDto> apiResponse = new ApiResponse<AttendanceDto>(
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }



    }

    public Integer getNoOfWorkingAttendanceOfEmp(Long empId) {

        List<String> status = Arrays.asList("PRESENT","HALF_DAY");

          int workingDays = attendanceRepository.countByEmployeeEmpIdAndAttendanceStatusIn(empId, status);
          return workingDays;
    }


}
