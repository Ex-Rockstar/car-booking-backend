package com.secBackend.cab_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

    //Driver Home Page
    @GetMapping("/home")
    public ResponseEntity<?> driverGreet(){
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Hello World "));
    }
}
