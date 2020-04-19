package com.parking.app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.app.enums.OccupancyStatus;
import com.parking.app.exception.NoSpotAvilableException;
import com.parking.app.exception.NoVehicleFoundException;
import com.parking.app.exception.VehicleNotFOundException;
import com.parking.app.exception.InvalidConfigException;
import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.client.ParkResponse;
import com.parking.app.model.client.TicketRequest;
import com.parking.app.model.client.TicketResponse;
import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.model.jpa.ParkingSpotDTO;
import com.parking.app.model.jpa.TicketDTO;
import com.parking.app.model.jpa.VehicleDTO;
import com.parking.app.repository.ParkRepository;
import com.parking.app.repository.SpotRepository;
import com.parking.app.repository.VehicleRepository;
import com.parking.app.service.AssignSpotService;
import com.parking.app.service.ParkService;
import com.parking.app.service.RateRuleService;
import com.parking.app.service.RetrieveParkingSpots;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class ParkServiceImpl implements ParkService {



    @Autowired
    ParkRepository parkRepository;

    @Autowired
    SpotRepository spotRepository;

    @Autowired
    RetrieveParkingSpots retrieveParkingSpots;

    @Autowired
    AssignSpotService assignSpotService;

    @Autowired
    RateRuleService rateRuleService;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${vehicle.supported.occupany}")
    String occupanyConfig;

    ReentrantLock reentrantLock = new ReentrantLock();

    @PersistenceContext
    private EntityManager manager;


    @Override
    @Transactional
    public ParkResponse park(ParkRequest parkRequest) {

        List<String> occupancyStatus = getOccupancyStatus(parkRequest);
        List<ParkingSpotDTO> freeParkingSpotDTOS = spotRepository.findByParkingLotIdHavingOccupancyStatus(parkRequest.getParkingLotID(), occupancyStatus);
        if (freeParkingSpotDTOS.isEmpty()) {
            throw new NoSpotAvilableException("No Parking Spot Avlaible for=" + parkRequest.getParkingLotID());
        }
        try {
            reentrantLock.lock();
            Optional<List<ParkingSpotDTO>> matchingParkingSpot = retrieveParkingSpots.getMatchingParkingSpot(parkRequest, occupancyStatus);
            if (!matchingParkingSpot.isPresent() || matchingParkingSpot.get().isEmpty()) {
                throw new NoSpotAvilableException("No Matching Parking Spot Availaible for=" + parkRequest.getParkingLotID());
            }
            return  assignSpotService.assignSlot(matchingParkingSpot.get(), parkRequest);
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override
    @Transactional
    public TicketResponse unpark(TicketRequest ticketRequest) {

        String registrationNumber = ticketRequest.getRegistrationNumber();
        Optional<VehicleDTO> vehicle = vehicleRepository.findByRegistrationNumber(registrationNumber);

        if(!vehicle.isPresent()){
            throw new VehicleNotFOundException("No vehicle found with "+registrationNumber);
        }
        //TODO conver to findAllbyId
        List<ParkDTO> parkingList = getAllParkings(vehicle.get().getVehicleId());
        //              orElseThrow(() -> new NoVehicleFoundException("No Vehicle found with registration=" + ticketRequest.getRegistrationNumber()));
        int totalSpotsHolded = parkingList.size();
        if(Objects.isNull(parkingList) || parkingList.isEmpty()){
            throw new NoVehicleFoundException("No Parking found with registration=" + registrationNumber);
        }

        ParkDTO parkDTO = parkingList.get(0);
        Date endTime = new Date();
        parkDTO.setEndTime(endTime);
        TicketDTO ticketDTO = rateRuleService.getRateDetails(parkDTO);
        List<ParkingSpotDTO> parkingSpotDTOList = new ArrayList<>();

        for (ParkDTO park_Ins :parkingList) {
            park_Ins.setEndTime(endTime);
            park_Ins.setAmount(ticketDTO.getAmount());
            ParkingSpotDTO parkingSpotDTO = spotRepository.findById(park_Ins.getSpotId()).orElseThrow(() -> new RuntimeException(""));
            parkingSpotDTO.setOccupancyCount(parkingSpotDTO.getOccupancyCount() - 1);
            parkingSpotDTO.setOccupancyStatus(parkingSpotDTO.getOccupancyCount() == 0 ? OccupancyStatus.EMPTY.toString() : OccupancyStatus.PARTIAL.toString());
            parkingSpotDTOList.add(parkingSpotDTO);

        }
        //un parking done
        parkRepository.saveAll(parkingList);
        //spots freed
        spotRepository.saveAll(parkingSpotDTOList);

        return new TicketResponse(parkDTO.getVehicleDTO().getVehicleId(), ticketDTO.getAmount()*totalSpotsHolded, parkDTO.getStartTime(), parkDTO.getEndTime(), ticketDTO.getDurationInHours());
        //return parkService.unpark(parkDTO);
    }

    public List<ParkDTO> getAllParkings(Integer vehicleId) {
        List<ParkDTO> parkings = manager.createNamedQuery("findParkingOfVehicle", ParkDTO.class)
                .setParameter(1, vehicleId)
                .getResultList();
        return parkings;
    }

    private List<String> getOccupancyStatus(ParkRequest parkRequest) {
        try {
            Map<String,String> vehicleOccupancyMap = objectMapper.readValue(occupanyConfig,HashMap.class);
            if(!vehicleOccupancyMap.containsKey(parkRequest.getVehicle().getVehicleType().name())){
                throw new InvalidConfigException("Invalid configuration for vehicle-occupancy");
            }
            String status = vehicleOccupancyMap.get(parkRequest.getVehicle().getVehicleType().name());
            return Arrays.asList(status.split(","));
        } catch (JsonProcessingException e) {
            throw new InvalidConfigException("Invalid configuration for vehicle-occupancy");
        }
    }
}
