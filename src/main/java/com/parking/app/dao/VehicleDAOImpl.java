package com.parking.app.dao;

import com.parking.app.model.client.TicketRequest;
import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.model.jpa.VehicleDTO;
import com.parking.app.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class VehicleDAOImpl implements VehicleDAO {

    @Autowired
    VehicleRepository vehicleRepository;


    @PersistenceContext
    private EntityManager manager;

    /*
        Retrieve Vehicle Details by Registration Number
     */
    @Override
    public Optional<VehicleDTO> getVehicleDetails(TicketRequest ticketRequest) {
        String registrationNumber = ticketRequest.getRegistrationNumber();
        return vehicleRepository.findByRegistrationNumber(registrationNumber);
    }

    /*
        Retrieve all parking spots held by vehicle. Royal customer will have multiple spots assigned others 1
     */
    @Override
    public List<ParkDTO> getAllParkings(Integer vehicleId) {
        List<ParkDTO> parkings = manager.createNamedQuery("findParkingOfVehicle", ParkDTO.class)
                .setParameter(1, vehicleId)
                .getResultList();
        return parkings;
    }
}
