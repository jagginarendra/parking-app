package com.parking.app.controller;

import com.parking.app.exception.InvalidRequestException;
import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.client.ParkResponse;
import com.parking.app.model.client.TicketRequest;
import com.parking.app.model.client.TicketResponse;
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
    public ParkResponse park(@Valid @RequestBody ParkRequest parkRequest) {

        if (Objects.isNull(parkRequest)) {
            throw new InvalidRequestException("Parking Request is invalid");
        }
        return parkService.park(parkRequest);
    }


    @Override
    @PostMapping("/unpark")
    public TicketResponse unpark(@Valid @RequestBody TicketRequest ticketRequest) {

        return parkService.unpark(ticketRequest);
    }
}
