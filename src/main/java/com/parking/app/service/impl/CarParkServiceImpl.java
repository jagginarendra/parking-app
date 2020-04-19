package com.parking.app.service.impl;

import com.parking.app.enums.OccupancyStatus;
import com.parking.app.exception.NoSpotAvilableException;
import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.client.ParkResponse;
import com.parking.app.model.client.TicketResponse;
import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.model.jpa.ParkingSpotDTO;
import com.parking.app.repository.ParkRepository;
import com.parking.app.repository.SpotRepository;
import com.parking.app.service.RetrieveParkingSpots;
import com.parking.app.service.VehicleParkingService;
import com.parking.app.utils.ParkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CarParkServiceImpl implements VehicleParkingService {


    private static final Logger logger = LoggerFactory.getLogger(CarParkServiceImpl.class);

    @Autowired
    ParkRepository parkRepository;

    @Autowired
    SpotRepository spotRepository;

    @Value("vehicle.parking.charges")
    String parkingCharges;

    @Autowired
    RetrieveParkingSpots retrieveParkingSpots;

    @Override
    public ParkResponse park(ParkRequest parkRequest, List<String> occupancyStatus) {
        Optional<ParkResponse> parkResponse = Optional.empty();


        Optional<ParkingSpotDTO> matchingParkingSpot = retrieveParkingSpots.getMatchingParkingSpot(parkRequest, occupancyStatus);
        if(!matchingParkingSpot.isPresent()){
            throw new NoSpotAvilableException("No Matching Parking Spot Availaible for=" + parkRequest.getParkingLotID());
        }

        ParkingSpotDTO parkingSpotDTO = matchingParkingSpot.get();
        int spotId = parkingSpotDTO.getSpotId();
        ParkDTO parkDTO = new ParkDTO(spotId, parkingSpotDTO.getCompanyId(), ParkUtil.getVehicleDetails(parkRequest.getVehicle(), parkRequest.getCustomer()));
        ParkDTO savedEntity = parkRepository.save(parkDTO);
        if (Objects.nonNull(savedEntity)) {
            parkingSpotDTO.setOccupancyCount(1);
            parkingSpotDTO.setOccupancyStatus(OccupancyStatus.FULL.toString());
            spotRepository.save(parkingSpotDTO);
            logger.info("Spot id=" + spotId + " vehicle saved=" + parkRequest.getVehicle().toString());
            parkResponse = Optional.of(new ParkResponse(spotId, savedEntity.getStartTime(), parkDTO.getVehicleDTO().getVehicleId()));
        }

        return parkResponse.get();
    }









    public TicketResponse unpark(ParkDTO parkDTO) {

        Date endTime = new Date();
        parkDTO.setEndTime(endTime);
        long amount = ParkUtil.getCharges(parkDTO, parkingCharges);
        parkDTO.setAmount(amount);
        parkRepository.save(parkDTO);
        ParkingSpotDTO parkingSpotDTO = spotRepository.findById(parkDTO.getSpotId()).orElseThrow(() -> new RuntimeException(""));
        parkingSpotDTO.setOccupancyCount(parkingSpotDTO.getOccupancyCount() - 1);
        parkingSpotDTO.setOccupancyStatus(parkingSpotDTO.getOccupancyCount() == 0 ? OccupancyStatus.EMPTY.toString() : OccupancyStatus.PARTIAL.toString());
        spotRepository.save(parkingSpotDTO);

        return new TicketResponse(parkDTO.getVehicleDTO().getVehicleId(), amount, parkDTO.getStartTime(), parkDTO.getEndTime());
    }
}
