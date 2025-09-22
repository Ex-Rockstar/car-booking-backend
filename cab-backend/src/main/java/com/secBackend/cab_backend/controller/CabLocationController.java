package com.secBackend.cab_backend.controller;

import com.secBackend.cab_backend.dataTansferObject.DriverLocation;
import com.secBackend.cab_backend.service.CabLocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cabs")
public class CabLocationController {

    private CabLocationService cabLocationService;
    public CabLocationController(CabLocationService cabLocationService) {
        this.cabLocationService = cabLocationService;
    }


    @PostMapping("/{driverId}/location")
    public ResponseEntity<?> updateLocation(@PathVariable Long driverId,
                                            @RequestBody Map<String, Double> payload) {
        cabLocationService.updateLocation(driverId, payload.get("latitude"), payload.get("longitude"));
        return ResponseEntity.ok(Map.of("message", "Location updated"));
    }
    @GetMapping("/{driverId}/location")
    public ResponseEntity<DriverLocation> getLocation(@PathVariable Long driverId) {
        DriverLocation location = cabLocationService.getLocation(driverId);
        if (location == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(location);
    }
    @GetMapping("/locations")
    public ResponseEntity<Map<Long, DriverLocation>> getAllLocations() {
        return ResponseEntity.ok(cabLocationService.getAllLocations());
    }

}
