package com.example.employeeManagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlController {

    @RequestMapping("/**")
    public ResponseEntity<String> wrongUrls() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("URL not found.");
    }
}
