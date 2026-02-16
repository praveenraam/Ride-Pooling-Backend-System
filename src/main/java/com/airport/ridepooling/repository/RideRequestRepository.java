package com.airport.ridepooling.repository;

import com.airport.ridepooling.entity.RideRequest;
import com.airport.ridepooling.entity.RideRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {
    long countByRideGroupIdAndStatus(Long rideGroupId, RideRequestStatus status);
}