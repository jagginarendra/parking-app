package com.parking.app.service.impl;

import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.client.ParkResponse;
import com.parking.app.model.client.TicketResponse;
import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.service.VehicleParkingService;

import java.util.List;

public class MotorCycleParkServiceImpl implements VehicleParkingService {

    @Override
    public ParkResponse park(ParkRequest parkRequest, List<String> occupancyStatus) {
        return null;
    }

    @Override
    public TicketResponse unpark(ParkDTO parkDTO) {
        return null;
    }

}
