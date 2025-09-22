package com.secBackend.cab_backend.dataTansferObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverHistoryDTO {
    private Long rideId;
    private Long customerId;
    private String customerName;
    private String customerPhone;
    private String pickUpLocation;
    private String dropOffLocation;
    private LocalDateTime acceptAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private Double distanceKm;
    private Double durationMinutes;
    private int fare;
    private String status;


}
