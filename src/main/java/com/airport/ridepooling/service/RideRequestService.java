package com.airport.ridepooling.service;

import com.airport.ridepooling.dto.RideRequestDto;
import com.airport.ridepooling.entity.*;
import com.airport.ridepooling.repository.CabRepository;
import com.airport.ridepooling.repository.RideGroupRepository;
import com.airport.ridepooling.repository.RideRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RideRequestService {

    private final RideRequestRepository rideRequestRepository;
    private final RideGroupRepository rideGroupRepository;
    private final PricingService pricingService;
    private final CabRepository cabRepository;


    public RideRequestService(
            RideRequestRepository rideRequestRepository,
            RideGroupRepository rideGroupRepository,
            PricingService pricingService,
            CabRepository cabRepository
    ) {
        this.rideRequestRepository = rideRequestRepository;
        this.rideGroupRepository = rideGroupRepository;
        this.pricingService = pricingService;
        this.cabRepository = cabRepository;
    }

    @Transactional
    public synchronized RideRequest createRideRequest(RideRequestDto dto) {

        RideRequest rideRequest = new RideRequest();

        rideRequest.setPassengerName(dto.getPassengerName());
        rideRequest.setPickupLocation(dto.getPickupLocation());
        rideRequest.setDropLocation(dto.getDropLocation());
        rideRequest.setLuggageCount(dto.getLuggageCount());
        rideRequest.setMaxDetourKm(dto.getMaxDetourKm());
        rideRequest.setStatus(RideRequestStatus.REQUESTED);
        double price = pricingService.calculatePrice(dto.getPickupLocation(), dto.getLuggageCount());

        rideRequest.setPrice(price);

        // Find compatible ride group
        List<RideGroup> groups =
                rideGroupRepository.findByPickupLocationAndDropLocationAndStatus(
                        dto.getPickupLocation(),
                        dto.getDropLocation(),
                        RideGroupStatus.ACTIVE
                );

        RideGroup assignedGroup = null;

        for (RideGroup group : groups) {

            if (group.getAvailableSeats() > 0 && group.getAvailableLuggage() >= dto.getLuggageCount()) {
                assignedGroup = group;
                break;
            }
        }

        if (assignedGroup == null) {
            List<Cab> availableCabs = cabRepository.findByStatus(CabStatus.AVAILABLE);

            if (availableCabs.isEmpty()) {
                throw new RuntimeException("No cabs available");
            }

            Cab cab = availableCabs.get(0);

            cab.setStatus(CabStatus.IN_USE);
            cabRepository.save(cab);

            RideGroup newGroup = new RideGroup();

            newGroup.setCabId(cab.getId());
            newGroup.setAvailableSeats(cab.getMaxSeats() - 1);
            newGroup.setAvailableLuggage(
                    cab.getMaxLuggage() - dto.getLuggageCount()
            );

            newGroup.setPickupLocation(dto.getPickupLocation());
            newGroup.setDropLocation(dto.getDropLocation());
            newGroup.setStatus(RideGroupStatus.ACTIVE);

            assignedGroup = rideGroupRepository.save(newGroup);

        } else {

            // Update existing group
            assignedGroup.setAvailableSeats(
                    assignedGroup.getAvailableSeats() - 1);
            assignedGroup.setAvailableLuggage(
                    assignedGroup.getAvailableLuggage() - dto.getLuggageCount());

            rideGroupRepository.save(assignedGroup);
        }

        rideRequest.setRideGroupId(assignedGroup.getId());
        rideRequest.setStatus(RideRequestStatus.ASSIGNED);

        return rideRequestRepository.save(rideRequest);
    }


    @Transactional
    public synchronized RideRequest cancelRide(Long rideRequestId) {

        RideRequest rideRequest = rideRequestRepository.findById(rideRequestId)
                .orElseThrow(() -> new RuntimeException("Ride request not found"));

        if (rideRequest.getStatus() == RideRequestStatus.CANCELLED) {
            throw new RuntimeException("Ride already cancelled");
        }
        Long groupId = rideRequest.getRideGroupId();

        if (groupId != null) {
            RideGroup group = rideGroupRepository.findById(groupId)
                    .orElseThrow(() -> new RuntimeException("Ride group not found"));

            group.setAvailableSeats(group.getAvailableSeats() + 1);
            group.setAvailableLuggage(
                    group.getAvailableLuggage() + rideRequest.getLuggageCount());

            if (group.getStatus() == RideGroupStatus.FULL) {
                group.setStatus(RideGroupStatus.ACTIVE);
            }

            rideGroupRepository.save(group);
        }

        rideRequest.setStatus(RideRequestStatus.CANCELLED);
        return rideRequestRepository.save(rideRequest);
    }

    public RideRequest getRideStatus(Long rideRequestId) {
        return rideRequestRepository.findById(rideRequestId)
                .orElseThrow(() -> new RuntimeException("Ride request not found"));

    }

    @Transactional
    public synchronized RideRequest completeRide(Long rideRequestId) {

        RideRequest rideRequest = rideRequestRepository.findById(rideRequestId)
                .orElseThrow(() ->
                        new RuntimeException("Ride request not found"));

        rideRequest.setStatus(RideRequestStatus.COMPLETED);
        rideRequestRepository.save(rideRequest);
        Long groupId = rideRequest.getRideGroupId();
        if (groupId != null) {

            long assignedCount =
                    rideRequestRepository.countByRideGroupIdAndStatus(
                            groupId,
                            RideRequestStatus.ASSIGNED
                    );
            if (assignedCount == 0) {
                RideGroup group = rideGroupRepository.findById(groupId)
                        .orElseThrow(() ->
                                new RuntimeException("Ride group not found"));
                group.setStatus(RideGroupStatus.COMPLETED);
                Cab cab = cabRepository.findById(group.getCabId())
                        .orElseThrow(() -> new RuntimeException("Cab not found"));
                cab.setStatus(CabStatus.AVAILABLE);
                cabRepository.save(cab);

                rideGroupRepository.save(group);
            }
        }

        return rideRequest;
    }
}
