package com.parking.app.service;

import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.client.ParkResponse;
import com.parking.app.model.jpa.ParkingSpotDTO;

import java.util.List;

public interface AssignSpotService {

    ParkResponse assignSlot(List<ParkingSpotDTO> parkingSpots , ParkRequest parkRequest);
}
