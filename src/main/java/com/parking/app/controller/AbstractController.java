package com.parking.app.controller;


import com.parking.app.model.client.TicketRequest;
import com.parking.app.model.client.TicketResponse;
import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.client.ParkResponse;

public abstract class AbstractController implements Controller {

    //TODO exception handler


    public ParkResponse park(ParkRequest parkRequest) {
        return null;
    }

    public TicketResponse unpark(TicketRequest ticketRequest) {
        return null;
    }

}
