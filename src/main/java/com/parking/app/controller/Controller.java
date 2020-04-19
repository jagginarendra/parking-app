package com.parking.app.controller;


import com.parking.app.model.client.TicketRequest;
import com.parking.app.model.client.TicketResponse;
import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.client.ParkResponse;

public interface Controller {

    ParkResponse park(ParkRequest request);

    TicketResponse unpark(TicketRequest request);


}
