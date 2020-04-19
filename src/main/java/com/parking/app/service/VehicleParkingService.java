package com.parking.app.service;

import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.client.ParkResponse;
import com.parking.app.model.client.TicketResponse;
import com.parking.app.model.jpa.ParkDTO;

import java.util.List;

public interface VehicleParkingService {

    ParkResponse park(ParkRequest parkRequest, List<String> occupancyStatus);

    TicketResponse unpark(ParkDTO parkDTO);
}
