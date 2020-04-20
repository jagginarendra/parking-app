package com.parking.app.controller;

import com.parking.app.exception.InvalidRequestException;
import com.parking.app.model.client.*;
import com.parking.app.service.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/parkingservice")
public class ParkingWebController extends AbstractController {

    @Autowired
    ParkService parkService;

    @Override
    @PostMapping("/park")
    public Response<ParkResponse> park(@Valid @RequestBody ParkRequest parkRequest) {

        if (Objects.isNull(parkRequest)) {
            throw new InvalidRequestException("Parking Request is invalid");
        }
        ParkResponse parkResponse = parkService.park(parkRequest);
        Response<ParkResponse> success = Response.success(parkResponse);
        return success;
    }


    @Override
    @PostMapping("/unpark")
    public Response<TicketResponse> unpark(@Valid @RequestBody TicketRequest ticketRequest) {

        if(Objects.isNull(ticketRequest)){
            throw new InvalidRequestException("Ticket Request is invalid");
        }
        TicketResponse ticketResponse = parkService.unpark(ticketRequest);
        Response<TicketResponse> success = Response.success(ticketResponse);
        return success;
    }
}
