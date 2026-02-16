package com.airport.ridepooling.repository;

import com.airport.ridepooling.entity.Cab;
import com.airport.ridepooling.entity.CabStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CabRepository extends JpaRepository<Cab, Long> {
    List<Cab> findByStatus(CabStatus status);
}