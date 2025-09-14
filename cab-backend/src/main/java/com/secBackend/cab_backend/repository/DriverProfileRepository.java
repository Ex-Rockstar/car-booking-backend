package com.secBackend.cab_backend.repository;

import com.secBackend.cab_backend.model.DriverProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//Driver Repository
public interface DriverProfileRepository extends JpaRepository<DriverProfile,Long> {
    Optional<DriverProfile> findByLicenseNumber(String licenseNumber);

    Optional<DriverProfile> findByVehicleNumber(String vehicleNumber);
}
