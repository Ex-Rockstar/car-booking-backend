package com.secBackend.cab_backend.service;

import com.secBackend.cab_backend.dataTransferObject.CustomerHomePageDto;
import com.secBackend.cab_backend.dataTransferObject.HistoryDTO;
import com.secBackend.cab_backend.model.RideRequest;
import com.secBackend.cab_backend.model.User;
import com.secBackend.cab_backend.repository.RideRequestRepository;
import com.secBackend.cab_backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final UserRepository userRepository;
    private final RideRequestRepository  rideRequestRepository;

    public CustomerService(UserRepository userRepository, RideRequestRepository rideRequestRepository) {
        this.userRepository = userRepository;
        this.rideRequestRepository = rideRequestRepository;
    }

    public ResponseEntity<?> getCustomerHomePage(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User currentUser = user.get();

        // Initialize DTO
        CustomerHomePageDto response = new CustomerHomePageDto();
        response.setCustomerId(currentUser.getId());
        response.setUserName(currentUser.getUsername());
        response.setEmail(currentUser.getEmail());

        // Get all rides for the customer
        List<RideRequest> thisUserAllRides = rideRequestRepository.findAllByUser_Id(currentUser.getId());
        List<RideRequest> removeUncompltedRides=thisUserAllRides.stream().filter(ride-> !ride.getStatus().toString().equals("COMPLETED")).toList();

        // Initialize counters
        int totalBookings = removeUncompltedRides.size();
        int totalSpent = 0;
        int totalTripBooking = 0;
        int totalInterCityBooking = 0;
        int totalRentalBooking = 0;
        int totalReserveBooking = 0;

        // Loop through rides to categorize and calculate
        for (RideRequest ride : thisUserAllRides) {
            // Assuming getFare() or getAmount() gives total ride cost
            if(!ride.getStatus().toString().equals("COMPLETED")){
                continue;
            }
            if (ride.getFare() != 0) {
                totalSpent += ride.getFare();
            }

            if (ride.getRideType() != null) {
                switch (ride.getRideType().toString()) {
                    case "LOCAL":
                        totalTripBooking++;
                        break;
                    case "INTERCITY":
                        totalInterCityBooking++;
                        break;
                    case "RENTAL":
                        totalRentalBooking++;
                        break;
                    case "ADVANCE":
                        totalReserveBooking++;
                        break;
                }
            }
        }

        // Populate DTO
        response.setTotalBookings(totalBookings);
        response.setTotalspent(totalSpent);
        response.setTotalTripBooking(totalTripBooking);
        response.setTotalInterCityBooking(totalInterCityBooking);
        response.setTotalRentalBooking(totalRentalBooking);
        response.setTotalReserveBooking(totalReserveBooking);

        System.out.println("customerHomePage loaded......");

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("data",response));
    }

    public ResponseEntity<?> getCustomerRideHistory(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
        }

        User currentUser = userOpt.get();

        // Get all rides for the user
        List<RideRequest> rides = rideRequestRepository.findAllByUser_Id(currentUser.getId());

        // Filter completed rides and map to DTO
        List<HistoryDTO> completedRides = rides.stream()
                .filter(ride -> ride.getStatus().toString().equals("COMPLETED"))
                .map(ride -> new HistoryDTO(
                        ride.getId(),
                        ride.getDriver() != null ? ride.getDriver().getId() : null,
                        ride.getDriver() != null ? ride.getDriver().getUsername() : "N/A",
                        ride.getDriver() != null ? ride.getDriver().getPhoneNumber() : "N/A",
                        ride.getPickUpLocation(),
                        ride.getDestinationLocation(),
                        ride.getAcceptedAt(),
                        ride.getStartedAt(),
                        ride.getCompletedAt(),
                        ride.getDistanceKm(),
                        (double) ride.getDurationMinutes(),
                        ride.getFare(),
                        ride.getStatus().toString(),
                        ride.getCabType() != null ? ride.getCabType().toString() : "N/A",
                        ride.getRideType() != null ? ride.getRideType().toString() : "N/A"
                ))
                .toList();

        return ResponseEntity.ok(Map.of("data", completedRides));
    }

}
