package com.parking.app.dao;

import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.jpa.ParkingSpotDTO;

import java.util.List;

public interface AvailabilityDAO {

    List<ParkingSpotDTO> checkAvailability(ParkRequest parkRequest);

}
