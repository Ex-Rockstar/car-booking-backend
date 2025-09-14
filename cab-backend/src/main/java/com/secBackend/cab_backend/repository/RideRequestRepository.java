package com.secBackend.cab_backend.repository;

import com.secBackend.cab_backend.model.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {
}
