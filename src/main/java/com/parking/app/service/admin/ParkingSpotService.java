package com.parking.app.service.admin;

import com.parking.app.model.client.AddNewSpotRequest;

public interface ParkingSpotService {


    boolean addNewSpots(AddNewSpotRequest newSpotRequest);

}
