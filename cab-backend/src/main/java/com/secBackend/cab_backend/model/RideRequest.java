package com.secBackend.cab_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class RideRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String pickUpLocation;

    @Column(nullable = false)
    private String destinationLocation;

    private double pickUpLatitude;
    private double pickUpLongitude;
    private double destinationLatitude;
    private double destinationLongitude;
    private int fare;
    private double distanceKm;
    private int durationMinutes;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User user;

    public enum RideStatus {
        PENDING,
        ACCEPTED,
        REJECTED,
        CANCELLED,
        COMPLETED
    }
}
