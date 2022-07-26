package com.api.parkingcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID> {
    
    boolean existsByLicensePlateCar(String licensePlateCar);
    boolean existsByParkingSpotNumber(String parkingSpotnumber);
    boolean existsByApartmentAndBlock(String apartment, String block);
}
