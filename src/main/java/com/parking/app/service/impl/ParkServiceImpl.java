package com.parking.app.service.impl;

import com.parking.app.exception.NoMatchingParkingFoundException;
import com.parking.app.exception.NoSpotAvilableException;
import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.client.ParkResponse;
import com.parking.app.model.client.TicketRequest;
import com.parking.app.model.client.TicketResponse;
import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.model.jpa.ParkingSpotDTO;
import com.parking.app.model.jpa.VehicleDTO;
import com.parking.app.service.ParkService;
import com.parking.app.service.SpotService;
import com.parking.app.service.VehicleService;
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
    SpotService spotService;

    @Autowired
    VehicleService vehicleService;

    ReentrantLock reentrantLock;

    public ParkServiceImpl(ReentrantLock reentrantLock) {
        this.reentrantLock = reentrantLock;
    }

    /*
        Takes ParkRequest, parking_Lot_Id is per parking company
        Assigns the spot to vehicle if avilable else throws NoSpotAvilableException
     */
    @Override
    @Transactional
    public ParkResponse park(ParkRequest parkRequest) {

        List<ParkingSpotDTO> freeParkingSpotDTOS = spotService.checkAvailability(parkRequest);
        if (freeParkingSpotDTOS.isEmpty()) {
            logger.error("No Spot available for {}", parkRequest.toString());
            throw new NoSpotAvilableException("No Parking Spot Available for={}" + parkRequest.getParkingLotID());
        }
        try {
            reentrantLock.lock();
            Optional<List<ParkingSpotDTO>> matchingParkingSpot = spotService.getMatchingParkingSpot(parkRequest);
            if (!matchingParkingSpot.isPresent() || matchingParkingSpot.get().isEmpty()) {
                logger.error("No Spot available for {}", parkRequest.toString());
                throw new NoSpotAvilableException("No Matching Parking Spot Available for=" + parkRequest.getParkingLotID());
            }
            List<ParkingSpotDTO> parkings = matchingParkingSpot.get();
            return spotService.assignSlot(parkings, parkRequest);
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

        Optional<VehicleDTO> vehicle = vehicleService.getVehicleDetails(ticketRequest);
        List<ParkDTO> parkingList = vehicleService.getAllParkingsHolded(vehicle.get().getVehicleId());
        if (Objects.isNull(parkingList) || parkingList.isEmpty()) {
            logger.error("No Matching Parking sport found for {}", ticketRequest.getRegistrationNumber());
            throw new NoMatchingParkingFoundException("No Parking found with registration=" + ticketRequest.getRegistrationNumber());
        }

        return spotService.releaseParkingSpot(parkingList);

    }


}
