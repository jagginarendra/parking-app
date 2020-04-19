package com.parking.app.service;

import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.model.jpa.TicketDTO;

public interface RateRuleService {

    TicketDTO getRateDetails(ParkDTO parkingSpotDTO);

}
