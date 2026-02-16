package com.airport.ridepooling.dto;

import lombok.Data;

@Data
public class RideRequestDto {

    private String passengerName;

    private String pickupLocation;

    private String dropLocation;

    private int luggageCount;

    private double maxDetourKm;

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
}