package com.parking.app.service.impl;

import com.google.common.base.Splitter;
import com.parking.app.enums.VehicleType;
import com.parking.app.exception.NoSpotAvilableException;
import com.parking.app.exception.NoVehicleFoundException;
import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.client.ParkResponse;
import com.parking.app.model.client.TicketRequest;
import com.parking.app.model.client.TicketResponse;
import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.model.jpa.ParkingSpotDTO;
import com.parking.app.repository.ParkRepository;
import com.parking.app.repository.SpotRepository;
import com.parking.app.repository.VehicleRepository;
import com.parking.app.service.ParkService;
import com.parking.app.service.VehicleParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ParkServiceImpl implements ParkService {


    @Autowired
    private Map<VehicleType, VehicleParkingService> parkServiceHashMap;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    ParkRepository parkRepository;

    @Autowired
    SpotRepository spotRepository;

    @Value("occupanyConfig")
    String occupanyConfig;

    ReentrantLock reentrantLock = new ReentrantLock();

    @Override
    public ParkResponse park(ParkRequest parkRequest) {

        VehicleParkingService parkService = parkServiceHashMap.get(parkRequest.getVehicle().getVehicleType());
        List<String> occupancyStatus = getOccupancyStatus(parkRequest);
        List<ParkingSpotDTO> freeParkingSpotDTOS = spotRepository.findByParkingLotIdAndOccupancyStatus(parkRequest.getParkingLotID(), occupancyStatus);
        if (freeParkingSpotDTOS.isEmpty()) {
            throw new NoSpotAvilableException("No Parking Spot Availaible for=" + parkRequest.getParkingLotID());
        }
        try{
            reentrantLock.lock();
            return parkService.park(parkRequest,occupancyStatus);
        }finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public TicketResponse unpark(TicketRequest ticketRequest) {
        VehicleParkingService parkService = parkServiceHashMap.get(ticketRequest.getVehicleType());
        ParkDTO parkDTO = parkRepository.findById(ticketRequest.getParkId()).
                orElseThrow(() ->new NoVehicleFoundException("No Vehicle found with registration="+ticketRequest.getRegistrationNumber()));
        return parkService.unpark(parkDTO);

    }

    private List<String> getOccupancyStatus(ParkRequest parkRequest) {

        List<String> occupancyStatus = new ArrayList<>();

        Map<String, String> map = Splitter.on(",").withKeyValueSeparator(":").split(occupanyConfig);

        map.get(parkRequest.getVehicle().getVehicleType().name());



        return occupancyStatus;
    }
}
