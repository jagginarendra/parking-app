package com.parking.app.service.impl;

import com.parking.app.dao.VehicleDAO;
import com.parking.app.exception.VehicleNotFoundException;
import com.parking.app.model.client.TicketRequest;
import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.model.jpa.VehicleDTO;
import com.parking.app.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleDAO vehicleDAO;

    private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Override
    public Optional<VehicleDTO> getVehicleDetails(TicketRequest ticketRequest) {

        Optional<VehicleDTO> vehicle = vehicleDAO.getVehicleDetails(ticketRequest);
        if (!vehicle.isPresent()) {
            logger.error("No Vehicle available with {}",ticketRequest.getRegistrationNumber());
            throw new VehicleNotFoundException("No vehicle found with " + ticketRequest.getRegistrationNumber());
        }
        return vehicle;
    }

    @Override
    public List<ParkDTO> getAllParkingsHolded(Integer vehicleId) {
        logger.info("Retrive parking for vehicle {}",vehicleId);
        return vehicleDAO.getAllParkings(vehicleId);
    }
}
