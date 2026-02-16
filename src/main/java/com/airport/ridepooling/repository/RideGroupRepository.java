package com.airport.ridepooling.repository;

import com.airport.ridepooling.entity.RideGroup;
import com.airport.ridepooling.entity.RideGroupStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideGroupRepository extends JpaRepository<RideGroup, Long> {
    List<RideGroup> findByPickupLocationAndDropLocationAndStatus(
            String pickupLocation,
            String dropLocation,
            RideGroupStatus status
    );
    List<RideGroup> findByPickupLocationAndStatus(
            String pickupLocation,
            RideGroupStatus status
    );
}