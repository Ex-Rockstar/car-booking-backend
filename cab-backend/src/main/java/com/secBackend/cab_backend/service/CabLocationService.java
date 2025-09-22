package com.secBackend.cab_backend.service;

import com.secBackend.cab_backend.dataTansferObject.DriverLocation;
import com.secBackend.cab_backend.model.User;
import com.secBackend.cab_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CabLocationService {

    private final Map<Long, DriverLocation> driverLocations = new ConcurrentHashMap<>();

    private UserRepository userRepository;
    public CabLocationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateLocation(Long driverId, Double latitude, Double longitude) {
        Optional<User> driver=userRepository.findById(driverId);

        driverLocations.put(driverId,new DriverLocation(driverId,latitude,longitude));

    }

    public DriverLocation getLocation(Long driverId) {
        return driverLocations.get(driverId);
    }


    public Map<Long, DriverLocation> getAllLocations() {
        return driverLocations;
    }

}
