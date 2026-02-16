package com.airport.ridepooling.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "ride_request",
    indexes = {
        @Index(name = "idx_ride_group_id", columnList = "rideGroupId"),
        @Index(name = "idx_ride_request_status", columnList = "status")
    }
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String passengerName;

    private String pickupLocation;

    private String dropLocation;

    private int luggageCount;

    private double maxDetourKm;

    @Enumerated(EnumType.STRING)
    private RideRequestStatus status;
    // REQUESTED, ASSIGNED, CANCELLED

    private Long rideGroupId;

    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public int getLuggageCount() {
        return luggageCount;
    }

    public void setLuggageCount(int luggageCount) {
        this.luggageCount = luggageCount;
    }

    public double getMaxDetourKm() {
        return maxDetourKm;
    }

    public void setMaxDetourKm(double maxDetourKm) {
        this.maxDetourKm = maxDetourKm;
    }

    public RideRequestStatus getStatus() {
        return status;
    }

    public void setStatus(RideRequestStatus status) {
        this.status = status;
    }

    public Long getRideGroupId() {
        return rideGroupId;
    }

    public void setRideGroupId(Long rideGroupId) {
        this.rideGroupId = rideGroupId;
    }
}
