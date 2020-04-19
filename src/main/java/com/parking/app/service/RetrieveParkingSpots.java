package com.parking.app.service;

import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.jpa.ParkingSpotDTO;

import java.util.List;
import java.util.Optional;

public interface RetrieveParkingSpots {


    Optional<List<ParkingSpotDTO>> getMatchingParkingSpot(ParkRequest parkRequest, List<String> occupancyStatus);

}
