package com.secBackend.cab_backend.repository;

import com.secBackend.cab_backend.model.RideRequest;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM RideRequest r WHERE r.id = :rideId")
    Optional<RideRequest> findByIdForUpdate(@Param("rideId") Long rideId);
    List<RideRequest> findAllByStatus(RideRequest.RideStatus status);

    List<RideRequest> findAllByUser_Id(Long customerId);

    List<RideRequest> findAllByDriver_Id(Long driverId);


}
