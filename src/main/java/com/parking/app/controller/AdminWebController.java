package com.parking.app.controller;

import com.parking.app.model.client.AddNewSpotRequest;
import com.parking.app.service.admin.AdminParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
    For Admin functionality, In progress. Add New floors,spots,companyId/parkingIds
 */
@RestController
@RequestMapping("/parkingservice/admin")
public class AdminWebController extends AbstractController {


    @Autowired
    AdminParkingSpotService adminParkingSpotService;

    @GetMapping("/addfloors")
    public boolean addFloors(){
        return false;
    }

    @PostMapping("/addspots")
    public boolean addParkingSpots(@RequestBody AddNewSpotRequest addNewSpotRequest){

        return adminParkingSpotService.addNewSpots(addNewSpotRequest);
    }
}
