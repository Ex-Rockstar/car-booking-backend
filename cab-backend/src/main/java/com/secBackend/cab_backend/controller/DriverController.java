package com.secBackend.cab_backend.controller;

import com.secBackend.cab_backend.dataTansferObject.DriverAvailabilityDto;
import com.secBackend.cab_backend.service.DriverService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

    private DriverService driverService;
    public DriverController(DriverService driverService){
        this.driverService = driverService;
    }

    @PostMapping("/availability")
    public ResponseEntity<?> updateDriverAvailability(Authentication authentication,
                                                     @RequestBody DriverAvailabilityDto driverAvailabilityDto) {
        return driverService.setDriverAvailability(authentication.getName(),driverAvailabilityDto.isAvailable());

    }

}
