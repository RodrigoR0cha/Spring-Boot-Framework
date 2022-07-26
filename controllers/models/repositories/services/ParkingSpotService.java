package com.api.parkingcontrol.services;

import com.api.parkingcontrol.repositories.ParkingSpotRepository;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
public class ParkingSpotService {

    final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository; 
    }
    
    @Transactional // Garante o rollback da aplicação
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        return parkingSpotRepository.save(parkingSpotModel); // retorno da requisição do ParkingSpotController
    }

    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block ) {
        return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
    }

    public List<ParkingSpotModel> findAll() {
        return parkingSpotRepository.findAll();
    }

    public Optional<ParkingSpotModel> findbyId(UUID id) {
        return parkingSpotRepository.findbyId(id);
    }

    public void delete(ParkingSpotModel parkingSpotModel) {
        parkingSpotRepository.delete(parkingSpotModel);
    }
}
