package com.parking.app.service.impl;

import com.parking.app.enums.OccupancyStatus;
import com.parking.app.enums.VehicleType;
import com.parking.app.exception.UnrecognizedCustomerTypeException;
import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.client.ParkResponse;
import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.model.jpa.ParkingSpotDTO;
import com.parking.app.model.jpa.VehicleDTO;
import com.parking.app.repository.CustomerRepository;
import com.parking.app.repository.ParkRepository;
import com.parking.app.repository.SpotRepository;
import com.parking.app.repository.VehicleRepository;
import com.parking.app.service.AssignSpotService;
import com.parking.app.utils.ParkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

public class AssignAssignSpotServiceImpl implements AssignSpotService {

    @Autowired
    ParkRepository parkRepository;

    @Autowired
    SpotRepository spotRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Value("${minimum.spots.for.royal}")
    Integer MIN_SPOTS_REUIRED_ROYAL;


    public static final Logger logger = LoggerFactory.getLogger(AssignAssignSpotServiceImpl.class);

    @Override
    public ParkResponse assignSlot(List<ParkingSpotDTO> parkingSpots, ParkRequest parkRequest) {
        switch (parkRequest.getCustomer().getCustomerType()){
            case ROYAL:
                return assignRoyalSpot(parkingSpots.subList(0,MIN_SPOTS_REUIRED_ROYAL), parkRequest);
            case GENERAL:
            case SENIOR_CITIZEN:
                return assignGeneralSpot(parkingSpots.get(0), parkRequest);
            default:
                throw new UnrecognizedCustomerTypeException("Unknown customer type supplied");
        }
    }


    private ParkResponse assignRoyalSpot(List<ParkingSpotDTO> parkingSpots, ParkRequest parkRequest) {

        //CustomerDTO customerDTO = customerRepository.save(ParkUtil.getCustomer(parkRequest.getCustomer()));
        VehicleDTO vehicleDetails = ParkUtil.getVehicleDetails(parkRequest.getVehicle());
        vehicleDetails.setCustomerDTO(ParkUtil.getCustomer(parkRequest.getCustomer()));
        vehicleRepository.save(vehicleDetails);
        List<ParkDTO> parkings = new ArrayList<>(parkingSpots.size());

        parkingSpots.forEach(spot-> {
            spot.setOccupancyCount(1);
            spot.setOccupancyStatus(OccupancyStatus.FULL.toString());
            parkings.add(new ParkDTO(spot.getSpotId(),parkRequest.getParkingLotID(), vehicleDetails));
        });
        parkRepository.saveAll(parkings);
        spotRepository.saveAll(parkingSpots);

        return new ParkResponse(parkings.get(0).getSpotId(),parkings.get(0).getStartTime(),vehicleDetails.getVehicleId());
    }

    // For motorcycle bottom spot max 3 , for upper max 2
    //Level 1 BOTTOM
    // Level 2 UPPER
    private ParkResponse assignGeneralSpot(ParkingSpotDTO parkingSpot, ParkRequest parkRequest) {
        int spotId = parkingSpot.getSpotId();
        ParkDTO parkDTO = new ParkDTO(spotId, parkingSpot.getCompanyId(), ParkUtil.getVehicleDetails(parkRequest.getVehicle(), parkRequest.getCustomer()));
        ParkDTO savedEntity = parkRepository.save(parkDTO);
        if(parkRequest.getVehicle().getVehicleType() == VehicleType.MOTORCYCLE){
            int maxCycleToAccommodate = parkingSpot.getLevel() == 1 ? 3 : 2;
            int currentCount = parkingSpot.getOccupancyCount()+1;
            parkingSpot.setOccupancyCount(currentCount);
            parkingSpot.setOccupancyStatus( maxCycleToAccommodate == currentCount ? OccupancyStatus.FULL.toString(): OccupancyStatus.PARTIAL.toString() );
        }else if(parkRequest.getVehicle().getVehicleType() == VehicleType.CAR){
            parkingSpot.setOccupancyStatus(OccupancyStatus.FULL.toString());
            parkingSpot.setOccupancyCount(1);
        }
        spotRepository.save(parkingSpot);
        logger.info("Spot id=" + spotId + " vehicle saved=" + parkRequest.getVehicle().toString());
        return new ParkResponse(spotId, savedEntity.getStartTime(), parkDTO.getVehicleDTO().getVehicleId());
    }


}
