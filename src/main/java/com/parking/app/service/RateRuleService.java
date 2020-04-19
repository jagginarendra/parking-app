package com.parking.app.service;

import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.model.jpa.Recipt;

public interface RateRuleService {

    Recipt getRateDetails(ParkDTO parkingSpotDTO);

}
