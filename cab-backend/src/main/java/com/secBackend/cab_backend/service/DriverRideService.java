package com.secBackend.cab_backend.service;

import com.secBackend.cab_backend.Util.GeoUtil;
import com.secBackend.cab_backend.dataTansferObject.DriverLocation;
import com.secBackend.cab_backend.dataTansferObject.DriverRideResponseDta;
import com.secBackend.cab_backend.model.RideRequest;
import com.secBackend.cab_backend.model.User;
import com.secBackend.cab_backend.repository.RideRequestRepository;
import com.secBackend.cab_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DriverRideService {

    private final RideRequestRepository rideRequestRepository;
    private final UserRepository userRepository;
    private final CabLocationService cabLocationService;

    public DriverRideService(RideRequestRepository rideRequestRepository,
                             UserRepository userRepository,
                             CabLocationService cabLocationService) {
        this.rideRequestRepository = rideRequestRepository;
        this.userRepository = userRepository;
        this.cabLocationService = cabLocationService;
    }

    @Transactional
    public ResponseEntity<?> startRide(Long rideId, String email) {
        User driver = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        RideRequest ride = rideRequestRepository.findByIdForUpdate(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (!ride.getDriver().getId().equals(driver.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Not your ride"));
        }

        if (ride.getStatus() != RideRequest.RideStatus.ACCEPTED) {
            return ResponseEntity.badRequest().body(Map.of("message", "Ride must be ACCEPTED to start"));
        }

        ride.setStatus(RideRequest.RideStatus.IN_PROGRESS);
        ride.setStartedAt(LocalDateTime.now());
        rideRequestRepository.save(ride);

        return ResponseEntity.ok(Map.of("message", "Ride started"));
    }

    @Transactional
    public ResponseEntity<?> completeRide(Long rideId, String email) {
        User driver = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        RideRequest ride = rideRequestRepository.findByIdForUpdate(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (!ride.getDriver().getId().equals(driver.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Not your ride"));
        }

        if (ride.getStatus() != RideRequest.RideStatus.IN_PROGRESS) {
            return ResponseEntity.badRequest().body(Map.of("message", "Ride must be IN_PROGRESS to complete"));
        }

        ride.setStatus(RideRequest.RideStatus.COMPLETED);
        ride.setCompletedAt(LocalDateTime.now());
        rideRequestRepository.save(ride);

        //make driver available again
        driver.getDriverProfile().setAvailable(true);
        userRepository.save(driver);

        return ResponseEntity.ok(Map.of("message", "Ride completed"));
    }
}
