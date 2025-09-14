package com.secBackend.cab_backend.service;

import com.secBackend.cab_backend.dataTansferObject.RideRequestDto;
import com.secBackend.cab_backend.dataTansferObject.RideResponseDto;
import com.secBackend.cab_backend.dataTansferObject.RouteResult;
import com.secBackend.cab_backend.model.RideRequest;
import com.secBackend.cab_backend.model.User;
import com.secBackend.cab_backend.repository.RideRequestRepository;
import com.secBackend.cab_backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RideRequestService {

    private final RideRequestRepository rideRequestRepository;
    private final RouteService routeService;
    private UserRepository userRepository;

    public RideRequestService(RideRequestRepository rideRequestRepository,
                              RouteService routeService, UserRepository userRepository) {
        this.rideRequestRepository = rideRequestRepository;
        this.routeService = routeService;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> createRide(RideRequestDto rideRequestDto,String email) {
        RouteResult route = routeService.getRoute(
                rideRequestDto.getPickUpLongitude(),
                rideRequestDto.getPickUpLatitude(),
                rideRequestDto.getDropOffLongitude(),
                rideRequestDto.getDropOffLatitude()
        );

        double baseFare;
        switch (rideRequestDto.getCabType().toUpperCase()) {
            case "SEDAN" -> baseFare = 15;
            case "SUV" -> baseFare = 20;
            default -> baseFare = 10; // MINI
        }
        double estimatedFare = baseFare * route.getDistanceKm();

        Optional<User> user=userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }
        RideRequest rideRequest = new RideRequest();
        rideRequest.setPickUpLocation(rideRequestDto.getPickUpLocation());
        rideRequest.setDestinationLocation(rideRequestDto.getDropOffLocation());
        rideRequest.setPickUpLatitude(rideRequestDto.getPickUpLatitude());
        rideRequest.setPickUpLongitude(rideRequestDto.getPickUpLongitude());
        rideRequest.setDestinationLatitude(rideRequestDto.getDropOffLatitude());
        rideRequest.setDestinationLongitude(rideRequestDto.getDropOffLongitude());
        rideRequest.setDistanceKm(route.getDistanceKm());
        rideRequest.setDurationMinutes((int) route.getDurationMinutes());
        rideRequest.setFare((int) estimatedFare);
        rideRequest.setStatus(RideRequest.RideStatus.PENDING);
        rideRequest.setUser(user.get());


        RideRequest saved = rideRequestRepository.save(rideRequest);
        
        RideResponseDto response = new RideResponseDto();
        response.setRideId(saved.getId());
        response.setDistance(saved.getDistanceKm());
        response.setDurationMinutes(saved.getDurationMinutes());
        response.setFare(saved.getFare());
        response.setStatus(saved.getStatus().name());

        return ResponseEntity.ok(response);
    }
}
