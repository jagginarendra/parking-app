package com.parking.app.dao;

import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.model.jpa.ParkingSpotDTO;
import com.parking.app.repository.ParkRepository;
import com.parking.app.repository.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class SpotDAOImpl implements SpotDAO {

    @Autowired
    SpotRepository spotRepository;

    @Autowired
    ParkRepository parkRepository;


    @Override
    public List<ParkingSpotDTO> checkAvailability(int parkingLotID, List<String> occupancyStatus) {
        return spotRepository.findByParkingLotIdHavingOccupancyStatus(parkingLotID, occupancyStatus);
    }

    @Override
    public List<ParkingSpotDTO> findEmptySpotsInParking(int parkingLotID, List<String> occupancyStatus) {
        return spotRepository.findByParkingLotIdHavingOccupancyStatus(parkingLotID, occupancyStatus);
    }

    @Override
    public List<ParkingSpotDTO> findSpotsForSeniorCitizen(int parkingLotID, List<String> occupancyStatus) {
        return spotRepository.findByCompanyIdHavingStatusInNaturalFloorOrderAndLevelOrder(parkingLotID, occupancyStatus);
    }

    @Override
    public List<ParkingSpotDTO> findSpotsForRoyal(int parkingLotID, List<String> occupancyStatus) {
        return spotRepository.findByCompanyIdHavingStatusInNaturalFloorOrder(parkingLotID, occupancyStatus);
    }

    @Override
    public List<ParkingSpotDTO> findSpotForGeneral(int parkingLotID, List<String> occupancyStatus) {
        return spotRepository.findByCompanyIdHavingStatusInReverseFloorOrder(parkingLotID, occupancyStatus);
    }

    @Override
    public Optional<ParkingSpotDTO> findBySpotId(Integer spotId) {
        return spotRepository.findById(spotId);
    }

    @Override
    public void saveUnParkingDetails(List<ParkDTO> parkingList, List<ParkingSpotDTO> parkingSpotDTOList) {
        //un parking done
        parkRepository.saveAll(parkingList);
        //spots freed
        spotRepository.saveAll(parkingSpotDTOList);
    }

    @Override
    public void save(ParkingSpotDTO parkingSpot) {
        spotRepository.save(parkingSpot);
    }

    @Override
    public void saveAll(List<ParkingSpotDTO> parkingSpots) {
        spotRepository.saveAll(parkingSpots);
    }


}
