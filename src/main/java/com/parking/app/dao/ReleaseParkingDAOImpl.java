package com.parking.app.dao;

import com.parking.app.enums.OccupancyStatus;
import com.parking.app.model.client.TicketResponse;
import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.model.jpa.ParkingSpotDTO;
import com.parking.app.model.jpa.Recipt;
import com.parking.app.repository.ParkRepository;
import com.parking.app.repository.SpotRepository;
import com.parking.app.service.RateRuleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReleaseParkingDAOImpl implements ReleaseParkingDAO {


    @Autowired
    ParkRepository parkRepository;

    @Autowired
    SpotRepository spotRepository;

    @Autowired
    RateRuleService rateRuleService;

    /*
        Release all spots held by Vehicle, royal vehicle can have multiple spots assigned
     */
    @Override
    public TicketResponse releaseParkingSpot(List<ParkDTO> parkingList) {
        int totalSpotsHolded = parkingList.size();
        ParkDTO parkDTO = parkingList.get(0);
        Date endTime = new Date();
        parkDTO.setEndTime(endTime);
        Recipt recipt = rateRuleService.getRateDetails(parkDTO);
        List<ParkingSpotDTO> parkingSpotDTOList = new ArrayList<>();
        for (ParkDTO park_Ins : parkingList) {
            park_Ins.setEndTime(endTime);
            park_Ins.setAmount(recipt.getAmount());
            ParkingSpotDTO parkingSpotDTO = spotRepository.findById(park_Ins.getSpotId()).orElseThrow(() -> new RuntimeException(""));
            parkingSpotDTO.setOccupancyCount(parkingSpotDTO.getOccupancyCount() - 1);
            parkingSpotDTO.setOccupancyStatus(parkingSpotDTO.getOccupancyCount() == 0 ? OccupancyStatus.EMPTY.toString() : OccupancyStatus.PARTIAL.toString());
            parkingSpotDTOList.add(parkingSpotDTO);

        }
        //un parking done
        parkRepository.saveAll(parkingList);
        //spots freed
        spotRepository.saveAll(parkingSpotDTOList);

        return new TicketResponse(parkDTO.getVehicleDTO().getVehicleId(), recipt.getAmount() * totalSpotsHolded, recipt.getStartDate(), recipt.getEndDate(), recipt.getDurationInHours());

    }
}
