package com.parking.app.service.impl;

import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.client.ParkResponse;
import com.parking.app.model.client.TicketResponse;
import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.repository.ParkRepository;
import com.parking.app.repository.SpotRepository;
import com.parking.app.service.VehicleParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
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



    @Override
    public ParkResponse park(ParkRequest parkRequest, List<String> occupancyStatus) {
        Optional<ParkResponse> parkResponse = Optional.empty();


       /* Optional<List<ParkingSpotDTO>> matchingParkingSpot = retrieveParkingSpots.getMatchingParkingSpot(parkRequest, occupancyStatus);
        if(!matchingParkingSpot.isPresent() || matchingParkingSpot.get().isEmpty()){
            throw new NoSpotAvilableException("No Matching Parking Spot Availaible for=" + parkRequest.getParkingLotID());
        }

        ParkResponse parkResponse1 = assignSpotService.assignSlot(matchingParkingSpot.get(), parkRequest);
*/


        return parkResponse.get();
    }









    public TicketResponse unpark(ParkDTO parkDTO) {

       return null;
    }
}
