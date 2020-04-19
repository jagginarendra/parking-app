package com.parking.app.service.impl;

import com.parking.app.exception.NoMatchingSpotFound;
import com.parking.app.exception.NoSpotAvilableException;
import com.parking.app.exception.UnrecognizedCustomerTypeException;
import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.jpa.ParkingSpotDTO;
import com.parking.app.repository.SpotRepository;
import com.parking.app.service.RetrieveParkingSpots;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class RetrieveParkingSpotsImpl implements RetrieveParkingSpots {

    @Autowired
    SpotRepository spotRepository;

    @Value("${minimum.spots.for.royal}")
    Integer MIN_SPOTS_REUIRED_ROYAL;

    /*
        Return multiple spots,As royals need multiple spot to occupy
     */
    @Override
    public Optional<List<ParkingSpotDTO>> getMatchingParkingSpot(ParkRequest parkRequest, List<String> occupancyStatus) {
        List<ParkingSpotDTO> currentFreeSpots = spotRepository.findByParkingLotIdHavingOccupancyStatus(parkRequest.getParkingLotID(), occupancyStatus);
        if (currentFreeSpots.isEmpty()) {
            Optional.empty();
        }
        switch (parkRequest.getCustomer().getCustomerType()) {

            case ROYAL:
                return getSpotForRoyal(parkRequest, occupancyStatus);
            case SENIOR_CITIZEN:
                return getSpotForSeniorCitizen(parkRequest, occupancyStatus);
            case GENERAL:
                return getGeneralSlots(parkRequest, occupancyStatus);
            default:
                throw new UnrecognizedCustomerTypeException("Unrecognized customer type passed");
        }
    }

    private Optional<List<ParkingSpotDTO>> getSpotForSeniorCitizen(ParkRequest parkRequest, List<String> occupancyStatus) {
        //sorted by floor and level
        List<ParkingSpotDTO> floorWiseSortedSpots = spotRepository.findByCompanyIdHavingStatusInNaturalFloorOrderAndLevelOrder(parkRequest.getParkingLotID(), occupancyStatus);
        if(floorWiseSortedSpots.isEmpty()){
            throw new NoMatchingSpotFound("No matching spot found");
        }
        return Optional.of(floorWiseSortedSpots.subList(0,1));
    }

    private Optional<List<ParkingSpotDTO>> getSpotForRoyal(ParkRequest parkRequest, List<String> occupancyStatus) {

        List<ParkingSpotDTO> floorWiseSortedSpots = spotRepository.findByCompanyIdHavingStatusInNaturalFloorOrder(parkRequest.getParkingLotID(), occupancyStatus);
        if(floorWiseSortedSpots.isEmpty()){
            throw new NoMatchingSpotFound("No matching spot found");
        }
        Map<Integer, List<ParkingSpotDTO>> floorWiseSpotsMap = floorWiseSortedSpots.parallelStream().collect(Collectors.groupingBy(e -> e.getFloorId()));
        Optional<List<ParkingSpotDTO>> matchedSpots = floorWiseSpotsMap.entrySet().stream().filter(e -> e.getValue().size() > MIN_SPOTS_REUIRED_ROYAL).map(e -> e.getValue()).findFirst();
        if(!matchedSpots.isPresent() || matchedSpots.get().isEmpty() || matchedSpots.get().size() < MIN_SPOTS_REUIRED_ROYAL){
            throw new NoSpotAvilableException("No Spot Available for Royal customer");
        }
        return matchedSpots;
    }

    private Optional<List<ParkingSpotDTO>> getGeneralSlots(ParkRequest parkRequest, List<String> occupancyStatus) {
        List<ParkingSpotDTO> floorWiseSortedSpots = spotRepository.findByCompanyIdHavingStatusInReverseFloorOrder(parkRequest.getParkingLotID(), occupancyStatus);
        if(floorWiseSortedSpots.isEmpty()){
            throw new NoMatchingSpotFound("No matching spot found");
        }
        return Optional.of(floorWiseSortedSpots.subList(0,1));
    }


}
