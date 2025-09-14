package com.secBackend.cab_backend.controller;

import com.secBackend.cab_backend.dataTansferObject.RideRequestDto;
import com.secBackend.cab_backend.service.RideRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer/rides")
public class RideRequestController {

    private final RideRequestService rideRequestService;

    public RideRequestController(RideRequestService rideRequestService) {
        this.rideRequestService = rideRequestService;
    }

    @PostMapping("/request")
    public ResponseEntity<?> requestRide(@RequestBody RideRequestDto rideRequestDto, Authentication authentication)
    {
        String email=authentication.getName();
        return rideRequestService.createRide(rideRequestDto,email);
    }
}
