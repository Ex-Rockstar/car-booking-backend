package com.secBackend.cab_backend.dataTansferObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverLocation {
    private Long driverId;
    private Double latitude;
    private Double longitude;
    private LocalDateTime updatedAt;

    public DriverLocation(Long driverId, Double latitude, Double longitude) {
        this.driverId = driverId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.updatedAt = LocalDateTime.now();
    }
}

