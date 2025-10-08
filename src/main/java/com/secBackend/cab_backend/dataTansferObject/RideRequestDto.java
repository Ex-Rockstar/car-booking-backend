package com.secBackend.cab_backend.dataTansferObject;

import lombok.Data;

@Data
public class RideRequestDto {
    private String pickUpLocation;
    private String dropOffLocation;
    private double pickUpLatitude;
    private double pickUpLongitude;
    private double dropOffLatitude;
    private double dropOffLongitude;
    private String cabType;
}
