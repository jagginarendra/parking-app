package com.parking.app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.app.dao.ParkDAO;
import com.parking.app.dao.SpotDAO;
import com.parking.app.enums.OccupancyStatus;
import com.parking.app.enums.VehicleType;
import com.parking.app.exception.NoMatchingParkingFoundException;
import com.parking.app.exception.NoMatchingSpotFound;
import com.parking.app.exception.NoSpotAvilableException;
import com.parking.app.exception.UnrecognizedCustomerTypeException;
import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.client.ParkResponse;
import com.parking.app.model.client.TicketResponse;
import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.model.jpa.ParkingSpotDTO;
import com.parking.app.model.jpa.Recipt;
import com.parking.app.model.jpa.VehicleDTO;
import com.parking.app.repository.VehicleRepository;
import com.parking.app.service.RateRuleService;
import com.parking.app.service.SpotService;
import com.parking.app.utils.ParkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class SpotServiceImpl implements SpotService {


    @Autowired
    ObjectMapper objectMapper;


    @Value("${vehicle.supported.occupany}")
    String occupanyConfig;

    @Value("${minimum.spots.for.royal}")
    Integer MIN_SPOTS_REUIRED_ROYAL;

    @Autowired
    SpotDAO spotDAO;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    RateRuleService rateRuleService;

    @Autowired
    ParkDAO parkDAO;


    public static final Logger logger = LoggerFactory.getLogger(SpotServiceImpl.class);

    @Override
    public List<ParkingSpotDTO> checkAvailability(ParkRequest parkRequest){
        List<String> occupancyStatus = ParkUtil.getOccupancyStatus(parkRequest, occupanyConfig, objectMapper);
        return spotDAO.checkAvailability(parkRequest.getParkingLotID(), occupancyStatus);
    }

    /*
       Return multiple spots,As royals need multiple spot to occupy
    */
    @Override
    public Optional<List<ParkingSpotDTO>> getMatchingParkingSpot(ParkRequest parkRequest) {
        List<String> occupancyStatus = ParkUtil.getOccupancyStatus(parkRequest, occupanyConfig, objectMapper);
        List<ParkingSpotDTO> currentFreeSpots = spotDAO.findEmptySpotsInParking(parkRequest.getParkingLotID(), occupancyStatus);
        if (currentFreeSpots.isEmpty()) {
            Optional.empty();
        }
        switch (parkRequest.getCustomer().getCustomerType()) {

            case ROYAL:
                return getSpotForRoyal(parkRequest, occupancyStatus);
            case SENIOR_CITIZEN:
                return getSpotForSeniorCitizen(parkRequest, occupancyStatus);
            case GENERAL:
                return getGeneralSlots(parkRequest, occupancyStatus);
            default:
                throw new UnrecognizedCustomerTypeException("Unrecognized customer type passed");
        }
    }

    private Optional<List<ParkingSpotDTO>> getSpotForSeniorCitizen(ParkRequest parkRequest, List<String> occupancyStatus) {
        //sorted by floor and level
        List<ParkingSpotDTO> floorWiseSortedSpots = spotDAO.findSpotsForSeniorCitizen(parkRequest.getParkingLotID(), occupancyStatus);
        if (floorWiseSortedSpots.isEmpty()) {
            throw new NoMatchingSpotFound("No matching spot found");
        }
        return Optional.of(floorWiseSortedSpots.subList(0, 1));
    }

    private Optional<List<ParkingSpotDTO>> getSpotForRoyal(ParkRequest parkRequest, List<String> occupancyStatus) {

        List<ParkingSpotDTO> floorWiseSortedSpots = spotDAO.findSpotsForRoyal(parkRequest.getParkingLotID(), occupancyStatus);
        if (floorWiseSortedSpots.isEmpty()) {
            throw new NoMatchingSpotFound("No matching spot found");
        }
        Map<Integer, List<ParkingSpotDTO>> floorWiseSpotsMap = floorWiseSortedSpots.parallelStream().collect(Collectors.groupingBy(e -> e.getFloorId()));
        Optional<List<ParkingSpotDTO>> matchedSpots = floorWiseSpotsMap.entrySet().stream().filter(e -> e.getValue().size() > MIN_SPOTS_REUIRED_ROYAL).map(e -> e.getValue()).findFirst();
        if (!matchedSpots.isPresent() || matchedSpots.get().isEmpty() || matchedSpots.get().size() < MIN_SPOTS_REUIRED_ROYAL) {
            throw new NoSpotAvilableException("No Spot Available for Royal customer");
        }
        return matchedSpots;
    }

    private Optional<List<ParkingSpotDTO>> getGeneralSlots(ParkRequest parkRequest, List<String> occupancyStatus) {
        List<ParkingSpotDTO> floorWiseSortedSpots = spotDAO.findSpotForGeneral(parkRequest.getParkingLotID(), occupancyStatus);
        if (floorWiseSortedSpots.isEmpty()) {
            throw new NoMatchingSpotFound("No matching spot found");
        }
        return Optional.of(floorWiseSortedSpots.subList(0, 1));
    }


    @Override
    public ParkResponse assignSlot(List<ParkingSpotDTO> parkingSpots, ParkRequest parkRequest) {
        switch (parkRequest.getCustomer().getCustomerType()) {
            case ROYAL:
                return assignRoyalSpot(parkingSpots.subList(0, MIN_SPOTS_REUIRED_ROYAL), parkRequest);
            case GENERAL:
            case SENIOR_CITIZEN:
                return assignGeneralSpot(parkingSpots.get(0), parkRequest);
            default:
                throw new UnrecognizedCustomerTypeException("Unknown customer type supplied");
        }
    }

    @Override
    public TicketResponse releaseParkingSpot(List<ParkDTO> parkingList) {

        if(parkingList.isEmpty()){
           throw new NoMatchingParkingFoundException("No parkings found");
        }
        int totalSpotsHolded = parkingList.size();
        ParkDTO parkDTO = parkingList.get(0);
        Date endTime = new Date();
        parkDTO.setEndTime(endTime);
        //TODO to enhance for custom rate rules charges
        Recipt recipt = rateRuleService.getRateDetails(parkDTO);
        List<ParkingSpotDTO> parkingSpotDTOList = new ArrayList<>();
        for (ParkDTO park_Ins : parkingList) {
            park_Ins.setEndTime(endTime);
            park_Ins.setAmount(recipt.getAmount());
            ParkingSpotDTO parkingSpotDTO = spotDAO.findBySpotId(park_Ins.getSpotId()).orElseThrow(() -> new NoMatchingSpotFound("No Spot found"));
            parkingSpotDTO.setOccupancyCount(parkingSpotDTO.getOccupancyCount() - 1);
            parkingSpotDTO.setOccupancyStatus(parkingSpotDTO.getOccupancyCount() == 0 ? OccupancyStatus.EMPTY.toString() : OccupancyStatus.PARTIAL.toString());
            parkingSpotDTOList.add(parkingSpotDTO);

        }
        spotDAO.saveUnParkingDetails(parkingList, parkingSpotDTOList);

        return new TicketResponse(parkDTO.getVehicleDTO().getVehicleId(), recipt.getAmount() * totalSpotsHolded, recipt.getStartDate(), recipt.getEndDate(), recipt.getDurationInHours());
    }

    /*
        Assigns multiple spot to Royal
     */
    private ParkResponse assignRoyalSpot(List<ParkingSpotDTO> parkingSpots, ParkRequest parkRequest) {

        VehicleDTO vehicleDetails = ParkUtil.getVehicleDetails(parkRequest.getVehicle());
        vehicleDetails.setCustomerDTO(ParkUtil.getCustomer(parkRequest.getCustomer()));
        vehicleRepository.save(vehicleDetails);
        List<ParkDTO> parkings = new ArrayList<>(parkingSpots.size());

        parkingSpots.forEach(spot -> {
            spot.setOccupancyCount(1);
            spot.setOccupancyStatus(OccupancyStatus.FULL.toString());
            parkings.add(new ParkDTO(spot.getSpotId(), parkRequest.getParkingLotID(), vehicleDetails));
        });
        parkDAO.saveAll(parkings);
        spotDAO.saveAll(parkingSpots);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return new ParkResponse(parkings.get(0).getSpotId(), formatter.format(parkings.get(0).getStartTime()), vehicleDetails.getVehicleId());
    }

    /*
        Level 1 BOTTOM
        Level 2 UPPER
        For Motorcycle occupancy status FULL,PARTIAL and EMPTY, also OCCUPANCY COUNT 3 FOR BOTTOM and 2 FOR UPPER LEVEL
        FOR CAR FULL,EMPTY
     */
    private ParkResponse assignGeneralSpot(ParkingSpotDTO parkingSpot, ParkRequest parkRequest) {
        int spotId = parkingSpot.getSpotId();
        Date date = new Date();
        ParkDTO parkDTO = new ParkDTO(spotId, parkingSpot.getCompanyId(), ParkUtil.getVehicleDetails(parkRequest.getVehicle(), parkRequest.getCustomer()));
        parkDAO.save(parkDTO);
        if (parkRequest.getVehicle().getVehicleType() == VehicleType.MOTORCYCLE) {
            int maxCycleToAccommodate = parkingSpot.getLevel() == 1 ? 3 : 2;
            int currentCount = parkingSpot.getOccupancyCount() + 1;
            parkingSpot.setOccupancyCount(currentCount);
            parkingSpot.setOccupancyStatus(maxCycleToAccommodate == currentCount ? OccupancyStatus.FULL.toString() : OccupancyStatus.PARTIAL.toString());
        } else if (parkRequest.getVehicle().getVehicleType() == VehicleType.CAR) {
            parkingSpot.setOccupancyStatus(OccupancyStatus.FULL.toString());
            parkingSpot.setOccupancyCount(1);
        }
        spotDAO.save(parkingSpot);
        logger.info("Spot id=" + spotId + " vehicle saved=" + parkRequest.getVehicle().toString());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return new ParkResponse(spotId, formatter.format(date), parkDTO.getVehicleDTO().getVehicleId());
    }

}
