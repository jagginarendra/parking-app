package com.parking.app.dao;

import com.parking.app.model.client.TicketResponse;
import com.parking.app.model.jpa.ParkDTO;

import java.util.List;

public interface ReleaseParkingDAO {


    TicketResponse releaseParkingSpot(List<ParkDTO> parkingList);
}
