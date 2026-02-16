package com.airport.ridepooling.controller;

import com.airport.ridepooling.dto.RideRequestDto;
import com.airport.ridepooling.entity.RideRequest;
import com.airport.ridepooling.service.RideRequestService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ride")
@Tag(name = "Ride Management", description = "Ride pooling APIs")
public class RideRequestController {

    private final RideRequestService rideRequestService;
    public RideRequestController(RideRequestService rideRequestService) {
        this.rideRequestService = rideRequestService;
    }

    @Operation(summary = "Create ride request from user side")
    @PostMapping("/request")
    public RideRequest createRideRequest(@RequestBody RideRequestDto dto) {
        return rideRequestService.createRideRequest(dto);
    }

    @Operation(summary = "Canceling the ride in the real time")
    @PostMapping("/cancel/{id}")
    public RideRequest cancelRide(@PathVariable Long id) {
        return rideRequestService.cancelRide(id);
    }

    @Operation(summary = "Getting the status of the ride")
    @GetMapping("/status/{id}")
    public RideRequest getRideStatus(@PathVariable Long id) {
        return rideRequestService.getRideStatus(id);
    }

    @Operation(summary = "Updating the status of the ride to 'Completed'")
    @PostMapping("/complete/{id}")
    public RideRequest completeRide(@PathVariable Long id) {
        return rideRequestService.completeRide(id);
    }

}
