package com.parking.app.service;


import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.client.TicketRequest;
import com.parking.app.model.client.TicketResponse;
import com.parking.app.model.client.ParkResponse;

public interface ParkService {

    ParkResponse park(ParkRequest parkRequest);

    TicketResponse unpark(TicketRequest ticketRequest);
}
