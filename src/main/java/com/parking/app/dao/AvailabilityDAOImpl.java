package com.parking.app.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.jpa.ParkingSpotDTO;
import com.parking.app.repository.SpotRepository;
import com.parking.app.utils.ParkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class AvailabilityDAOImpl implements AvailabilityDAO {

    @Autowired
    ObjectMapper objectMapper;

    @Value("${vehicle.supported.occupany}")
    String occupanyConfig;

    @Autowired
    SpotRepository spotRepository;

    public List<ParkingSpotDTO> checkAvailability(ParkRequest parkRequest){
        List<String> occupancyStatus = ParkUtil.getOccupancyStatus(parkRequest, occupanyConfig, objectMapper);
        return spotRepository.findByParkingLotIdHavingOccupancyStatus(parkRequest.getParkingLotID(), occupancyStatus);
    }

}
