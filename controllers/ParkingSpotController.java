package com.api.parkingcontrol.Controllers;

import com.api.parkingcontrol.services.ParkingSpotService;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot") // endpoints
public class ParkingSpotController { 

    final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) { // método Construtor
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping // Trata requisiçoes Post
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        if(parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: A licença desta placa já está em uso!");
        }
        if(parkingSpotService.existsByLicenseNumber(parkingSpotDto.getParkingSpotNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Essa vaga de estacionamento já está em uso!");
        }
        if(parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Vaga de estacionamento já registrada para este bloco de apartamentos!");
        }
    
        var ParkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, ParkingSpotModel);
        ParkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(ParkingSpotService.save(ParkingSpotModel)); 
    }
    
    @GetMapping
    public ResponseEntity<LIST<ParkingSpotModel>> getAllParkingSpots() {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") UUID id) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga de estacionamento não encontrada!");
        }
        return ResponseEntity.status(HttpStatus.Ok).body(parkingSpotModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga de estacionamento não encontrada!");
        }
        parkingSpotService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.Ok).body("Vaga de estacionamento deletada com sucesso!");
    }

    @PutMapping // Trata requisiçoes Post
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id, @RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        if(parkingSpotService.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga de Estacionamento não encontrada!");
        }
        
        var ParkingSpotModel = parkingSpotModelOptional.get();
        ParkingSpotModel.setParkingSpotNumber(parkingSpotDto.getParkingSpotNumber());
        ParkingSpotModel.setLicensePlateCar(parkingSpotDto.getLicensePlateCar());
        ParkingSpotModel.setModelCar(parkingSpotDto.getModelCar());
        ParkingSpotModel.setBrandCar(parkingSpotDto.getBrandCar());
        ParkingSpotModel.setColorCar(parkingSpotDto.getColorCar());
        ParkingSpotModel.setResponsibleName(parkingSpotDto.getResponsibleName);
        ParkingSpotModel.setApartment(parkingSpotDto.getApartment());
        ParkingSpotModel.setBlock(parkingSpotDto.getBlock());

        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel()));
    }
    

}
