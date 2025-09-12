package com.secBackend.cab_backend.repository;

import com.secBackend.cab_backend.model.DriverProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//Driver Repository
public interface driverProfileRepository extends JpaRepository<DriverProfile,Long> {
}
