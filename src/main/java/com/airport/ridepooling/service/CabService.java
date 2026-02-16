package com.airport.ridepooling.service;

import com.airport.ridepooling.dto.CabDto;
import com.airport.ridepooling.entity.Cab;
import com.airport.ridepooling.entity.CabStatus;
import com.airport.ridepooling.repository.CabRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CabService {

    private final CabRepository cabRepository;

    public CabService(CabRepository cabRepository) {
        this.cabRepository = cabRepository;
    }

    public Cab createCab(CabDto dto) {
        Cab cab = new Cab();

        cab.setCabNumber(dto.getCabNumber());
        cab.setMaxSeats(dto.getMaxSeats());
        cab.setMaxLuggage(dto.getMaxLuggage());
        cab.setStatus(CabStatus.AVAILABLE);

        return cabRepository.save(cab);
    }

    public Cab markOutOfService(Long cabId) {
        Cab cab = cabRepository.findById(cabId)
                .orElseThrow(() -> new RuntimeException("Cab not found"));

        cab.setStatus(CabStatus.OUT_OF_SERVICE);
        return cabRepository.save(cab);
    }

    public Cab markAvailable(Long cabId) {
        Cab cab = cabRepository.findById(cabId)
                .orElseThrow(() -> new RuntimeException("Cab not found"));

        cab.setStatus(CabStatus.AVAILABLE);
        return cabRepository.save(cab);
    }

    public List<Cab> getAllCabs() {
        return cabRepository.findAll();
    }

    public List<Cab> getAvailableCabs() {
        return cabRepository.findByStatus(CabStatus.AVAILABLE);
    }

    public Cab getCabById(Long id){
        return cabRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cab not found"));
    }

}
