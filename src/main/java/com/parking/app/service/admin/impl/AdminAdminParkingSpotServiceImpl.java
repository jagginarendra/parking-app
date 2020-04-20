package com.parking.app.service.admin.impl;

import com.parking.app.enums.OccupancyStatus;
import com.parking.app.exception.NoFloorsPresentException;
import com.parking.app.model.client.AddNewSpotRequest;
import com.parking.app.model.jpa.FloorDTO;
import com.parking.app.model.jpa.ParkingSpotDTO;
import com.parking.app.repository.FloorRepository;
import com.parking.app.repository.SpotRepository;
import com.parking.app.service.admin.AdminParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/*
    Adds New Parking Statpots with Initial State as Empty
 */
public class AdminAdminParkingSpotServiceImpl implements AdminParkingSpotService {

    @Autowired
    SpotRepository spotRepository;

    @Autowired
    FloorRepository floorRepository;

    //Warm up for demo
    @Override
    public boolean addNewSpots(AddNewSpotRequest newSpotRequest) {

        final int ZERO = 0;
        int companyId = newSpotRequest.getCompanyId();
        int numberOfSpots = newSpotRequest.getNumberOfSpots();
        List<FloorDTO> floors = floorRepository.findAll();
        if (floors.isEmpty()) {
            throw new NoFloorsPresentException("No Floor present to add spots");
        }
        List<ParkingSpotDTO> existingSpots = spotRepository.findByCompanyId(companyId);
        if (existingSpots != null && existingSpots.size() == 0) {

            List<ParkingSpotDTO> parkingSpotDTOS = new ArrayList<>(numberOfSpots * 2);
            for (int counter = 0; counter < floors.size(); counter++) {
                int floorId = floors.get(counter).getId();
                for (int spotsCounter = 0; spotsCounter < numberOfSpots; spotsCounter++) {
                    parkingSpotDTOS.add(new ParkingSpotDTO(floorId, 1, OccupancyStatus.EMPTY.toString(), ZERO, companyId));
                    parkingSpotDTOS.add(new ParkingSpotDTO(floorId, 2, OccupancyStatus.EMPTY.toString(), ZERO, companyId));
                }
            }
            List<ParkingSpotDTO> updtaedDtos = spotRepository.saveAll(parkingSpotDTOS);
            if (updtaedDtos != null && updtaedDtos.size() == parkingSpotDTOS.size()) {
                return true;
            } else {
                return false;
            }

        }


        return false;
    }
}
