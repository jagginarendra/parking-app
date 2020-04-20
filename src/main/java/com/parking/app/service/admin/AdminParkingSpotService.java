package com.parking.app.service.admin;

import com.parking.app.model.client.AddNewSpotRequest;

public interface AdminParkingSpotService {


    boolean addNewSpots(AddNewSpotRequest newSpotRequest);

}
