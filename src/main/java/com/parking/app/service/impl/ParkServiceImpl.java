package com.parking.app.service.impl;

import com.parking.app.dao.*;
import com.parking.app.exception.NoMatchingParkingFoundException;
import com.parking.app.exception.NoSpotAvilableException;
import com.parking.app.exception.VehicleNotFoundException;
import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.client.ParkResponse;
import com.parking.app.model.client.TicketRequest;
import com.parking.app.model.client.TicketResponse;
import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.model.jpa.ParkingSpotDTO;
import com.parking.app.model.jpa.VehicleDTO;
import com.parking.app.service.ParkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class ParkServiceImpl implements ParkService {

    private static final Logger logger = LoggerFactory.getLogger(ParkServiceImpl.class);

    @Autowired
    RetrieveParkingSpotsDAO retrieveParkingSpotsDAO;

    @Autowired
    AssignSpotDAO assignSpotDAO;

    @Autowired
    AvailabilityDAO availabilityDAO;

    @Autowired
    VehicleDAO vehicleDAO;


    @Autowired
    ReleaseParkingDAO releaseParkingDAO;

    ReentrantLock reentrantLock = new ReentrantLock();

    /*
        Takes ParkRequest, parking_Lot_Id is per parking company
        Assigns the spot to vehicle if avilable else throws NoSpotAvilableException
     */
    @Override
    @Transactional
    public ParkResponse park(ParkRequest parkRequest) {

        List<ParkingSpotDTO> freeParkingSpotDTOS = availabilityDAO.checkAvailability(parkRequest);
        if (freeParkingSpotDTOS.isEmpty()) {
            logger.error("No Spot available for "+parkRequest.toString());
            throw new NoSpotAvilableException("No Parking Spot Available for=" + parkRequest.getParkingLotID());
        }
        try {
            reentrantLock.lock();
            Optional<List<ParkingSpotDTO>> matchingParkingSpot = retrieveParkingSpotsDAO.getMatchingParkingSpot(parkRequest);
            if (!matchingParkingSpot.isPresent() || matchingParkingSpot.get().isEmpty()) {
                logger.error("No Spot available for "+parkRequest.toString());
                throw new NoSpotAvilableException("No Matching Parking Spot Available for=" + parkRequest.getParkingLotID());
            }
            return assignSpotDAO.assignSlot(matchingParkingSpot.get(), parkRequest);
        } finally {
            reentrantLock.unlock();
        }
    }

    /*
        Returns ticket response , after successful unparking , throws VehicleNotFoundException if vehicle not present in DB
     */
    @Override
    @Transactional
    public TicketResponse unpark(TicketRequest ticketRequest) {

        Optional<VehicleDTO> vehicle = vehicleDAO.getVehicleDetails(ticketRequest);

        if (!vehicle.isPresent()) {
            logger.error("No Vehicle available with "+ticketRequest.getRegistrationNumber());
            throw new VehicleNotFoundException("No vehicle found with " + ticketRequest.getRegistrationNumber());
        }
        List<ParkDTO> parkingList = vehicleDAO.getAllParkings(vehicle.get().getVehicleId());
        if (Objects.isNull(parkingList) || parkingList.isEmpty()) {
            logger.error("No Matching Parking sport found for "+ticketRequest.getRegistrationNumber());
            throw new NoMatchingParkingFoundException("No Parking found with registration=" + ticketRequest.getRegistrationNumber());
        }

        return releaseParkingDAO.releaseParkingSpot(parkingList);

    }


}
