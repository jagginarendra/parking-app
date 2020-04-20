package com.parking.app.service;

import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.client.ParkResponse;
import com.parking.app.model.client.TicketResponse;
import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.model.jpa.ParkingSpotDTO;

import java.util.List;
import java.util.Optional;

public interface SpotService {

    List<ParkingSpotDTO> checkAvailability(ParkRequest parkRequest);

    Optional<List<ParkingSpotDTO>> getMatchingParkingSpot(ParkRequest parkRequest);

    ParkResponse assignSlot(List<ParkingSpotDTO> parkingSpots , ParkRequest parkRequest);

    TicketResponse releaseParkingSpot(List<ParkDTO> parkingList);
}
