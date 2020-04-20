package com.parking.app.dao;

import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.model.jpa.ParkingSpotDTO;

import java.util.List;
import java.util.Optional;

public interface SpotDAO {

    List<ParkingSpotDTO> checkAvailability(int parkingLotID, List<String> occupancyStatus);

    List<ParkingSpotDTO> findEmptySpotsInParking(int parkingLotID, List<String> occupancyStatus);


    List<ParkingSpotDTO> findSpotsForSeniorCitizen(int parkingLotID, List<String> occupancyStatus);

    List<ParkingSpotDTO> findSpotsForRoyal(int parkingLotID, List<String> occupancyStatus);

    List<ParkingSpotDTO> findSpotForGeneral(int parkingLotID, List<String> occupancyStatus);

    Optional<ParkingSpotDTO> findBySpotId(Integer spotId);

    void saveUnParkingDetails(List<ParkDTO> parkingList, List<ParkingSpotDTO> parkingSpotDTOList);

    void save(ParkingSpotDTO parkingSpot);

    void saveAll(List<ParkingSpotDTO> parkingSpots);
}
