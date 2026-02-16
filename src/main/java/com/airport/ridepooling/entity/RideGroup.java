package com.airport.ridepooling.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "ride_group",
    indexes = {
        @Index(name = "idx_pickup_location", columnList = "pickupLocation"),
        @Index(name = "idx_drop_location", columnList = "dropLocation"),
        @Index(name = "idx_ride_group_status", columnList = "status")
    }
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pickupLocation;

    private String dropLocation;

    private int availableSeats;

    private int availableLuggage;

    private Long cabId;

    @Enumerated(EnumType.STRING)
    private RideGroupStatus status;
    // ACTIVE, FULL, COMPLETED

    public Long getCabId() {
        return cabId;
    }

    public void setCabId(Long cabId) {
        this.cabId = cabId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getAvailableLuggage() {
        return availableLuggage;
    }

    public void setAvailableLuggage(int availableLuggage) {
        this.availableLuggage = availableLuggage;
    }

    public RideGroupStatus getStatus() {
        return status;
    }

    public void setStatus(RideGroupStatus status) {
        this.status = status;
    }
}