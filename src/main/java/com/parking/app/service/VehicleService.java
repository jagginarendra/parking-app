package com.parking.app.service;

import com.parking.app.model.client.TicketRequest;
import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.model.jpa.VehicleDTO;

import java.util.List;
import java.util.Optional;

public interface VehicleService {

    Optional<VehicleDTO> getVehicleDetails(TicketRequest ticketRequest);

    List<ParkDTO> getAllParkingsHolded(Integer vehicleId);

}
