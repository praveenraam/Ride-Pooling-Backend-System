package com.airport.ridepooling.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.airport.ridepooling.entity.Cab;
import com.airport.ridepooling.service.CabService;
import org.springframework.web.bind.annotation.*;

import com.airport.ridepooling.dto.CabDto;
import java.util.List;

@RestController
@RequestMapping("/cab")
@Tag(name = "Cab Management", description = "CAB CRUD and Status Updation APIs")
public class CabController {
    private final CabService cabService;

    public CabController(CabService cabService) {
        this.cabService = cabService;
    }

    @Operation(summary = "To mark cab as out of service ")
    @PostMapping("/out-of-service/{id}")
    public Cab markOutOfService(@PathVariable Long id) {
        return cabService.markOutOfService(id);
    }

    @Operation(summary = "To mark cab as available")
    @PostMapping("/available/{id}")
    public Cab markAvailable(@PathVariable Long id) {
        return cabService.markAvailable(id);
    }

    @Operation(summary = "Create a cab")
    @PostMapping("/create")
    public Cab createCab(@RequestBody CabDto dto) {
        return cabService.createCab(dto);
    }

    @Operation(summary = "get all cab")
    @GetMapping("/all")
    public List<Cab> getAllCabs() {
        return cabService.getAllCabs();
    }

    @Operation(summary = "get cab that are available for forming ride group")
    @GetMapping("/available")
    public List<Cab> getAvailableCabs() {
        return cabService.getAvailableCabs();
    }

    @GetMapping("/{id}")
    public Cab getCab(@PathVariable Long id) {
        return cabService.getCabById(id);
    }

}
