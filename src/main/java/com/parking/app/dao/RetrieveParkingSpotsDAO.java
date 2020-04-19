package com.parking.app.dao;

import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.jpa.ParkingSpotDTO;

import java.util.List;
import java.util.Optional;

public interface RetrieveParkingSpotsDAO {


    Optional<List<ParkingSpotDTO>> getMatchingParkingSpot(ParkRequest parkRequest);

}
