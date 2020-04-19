package com.parking.app.dao;

import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.client.ParkResponse;
import com.parking.app.model.jpa.ParkingSpotDTO;

import java.util.List;

public interface AssignSpotDAO {

    ParkResponse assignSlot(List<ParkingSpotDTO> parkingSpots , ParkRequest parkRequest);
}
