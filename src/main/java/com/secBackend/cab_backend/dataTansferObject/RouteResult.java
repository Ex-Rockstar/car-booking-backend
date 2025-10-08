package com.secBackend.cab_backend.dataTansferObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteResult {
    private double distanceKm;
    private double durationMinutes;
}
