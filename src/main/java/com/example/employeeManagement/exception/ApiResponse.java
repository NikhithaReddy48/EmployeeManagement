package com.example.employeeManagement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private String message;
    private int errorCode;
    private Long timeStamp = System.currentTimeMillis();

    public ApiResponse(T data, String message) {
        this.success = true;
        this.data = data;
        this.message = message;
        this.errorCode = 0;
    }
    public ApiResponse(String message, int errorCode) {
        this.success = false;
        this.data = null;
        this.message = message;
        this.errorCode = errorCode;
    }
}
