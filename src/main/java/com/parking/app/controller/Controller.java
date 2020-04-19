package com.parking.app.controller;


import com.parking.app.model.client.*;

public interface Controller {

    Response<ParkResponse> park(ParkRequest request);

    Response<TicketResponse> unpark(TicketRequest request);


}
