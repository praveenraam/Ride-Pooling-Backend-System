package com.airport.ridepooling.service;

import com.airport.ridepooling.entity.RideGroup;
import com.airport.ridepooling.entity.RideGroupStatus;
import com.airport.ridepooling.repository.RideGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingService {

    private final RideGroupRepository rideGroupRepository;

    public PricingService(RideGroupRepository rideGroupRepository) {
        this.rideGroupRepository = rideGroupRepository;
    }

    public double calculatePrice(String pickupLocation, int luggageCount) {

        double baseFare = 100;

        double distanceFare = 50; // If we have Km count, we can multiply

        double luggageFare = luggageCount * 10;

        double subtotal = baseFare + distanceFare + luggageFare;

        double demandMultiplier = calculateDemandMultiplier(pickupLocation);

        double poolingDiscount = 0.8; // 20% discount

        return subtotal * demandMultiplier * poolingDiscount;
    }

    private double calculateDemandMultiplier(String pickupLocation) {

        List<RideGroup> activeGroups =
                rideGroupRepository.findByPickupLocationAndStatus(
                        pickupLocation, RideGroupStatus.ACTIVE);

        int demand = activeGroups.size();

        if (demand < 2) return 1.0;
        if (demand < 5) return 1.2;
        if (demand < 10) return 1.5;

        return 2.0;
    }
}
