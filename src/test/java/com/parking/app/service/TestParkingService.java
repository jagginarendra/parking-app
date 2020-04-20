package com.parking.app.service;

import com.parking.app.enums.CustomerType;
import com.parking.app.enums.OccupancyStatus;
import com.parking.app.enums.VehicleType;
import com.parking.app.exception.NoSpotAvilableException;
import com.parking.app.model.client.Customer;
import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.client.ParkResponse;
import com.parking.app.model.client.Vehicle;
import com.parking.app.model.jpa.ParkingSpotDTO;
import com.parking.app.service.impl.ParkServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TestParkingService {


    @Mock
    SpotService spotService;

    @Mock
    VehicleService vehicleService;

    @InjectMocks
    ParkService parkService = new ParkServiceImpl(new ReentrantLock());


    @Test()
    public void testNoSpotAvialibleException(){
        ParkRequest parkRequest = getParkRequest();
        Mockito.when(spotService.checkAvailability(parkRequest)).thenReturn(new ArrayList<>());

        assertThrows(NoSpotAvilableException.class, () -> {
                parkService.park(parkRequest);
        });
    }


    @Test()
    public void testNoMatchingSpotExceptionAfterInitialCheck(){
        ParkRequest parkRequest = getParkRequest();
        Mockito.when(spotService.checkAvailability(parkRequest)).thenReturn(getFreeParkingSpotDTOS());
        Mockito.when(spotService.getMatchingParkingSpot(parkRequest)).thenReturn(Optional.empty());
        assertThrows(NoSpotAvilableException.class, () -> {
            parkService.park(parkRequest);
        });
    }

    @Test()
    public void testMatchingSpotAvilable(){
        ParkRequest parkRequest = getParkRequest();
        ParkResponse parkResponse = new ParkResponse(34, "25-04-2020 13:25:45",4500);
        Mockito.when(spotService.checkAvailability(parkRequest)).thenReturn(getFreeParkingSpotDTOS());
        Mockito.when(spotService.getMatchingParkingSpot(parkRequest)).thenReturn(Optional.of(getFreeParkingSpotDTOS()));
        //Mockito.when(spotService.assignSlot(getFreeParkingSpotDTOS(),parkRequest)).thenReturn(parkResponse);
        ParkResponse returnParkResponse = parkService.park(parkRequest);
        assertEquals(parkResponse.getVehicleId(), returnParkResponse.getVehicleId());
    }



    private List<ParkingSpotDTO> getFreeParkingSpotDTOS (){
        ParkingSpotDTO parkingSpotDTO = new ParkingSpotDTO(1,1, OccupancyStatus.EMPTY.toString(),0,5000);
        return Arrays.asList(parkingSpotDTO);
    }



    private ParkRequest getParkRequest(){
        Vehicle vehicle = new Vehicle(VehicleType.CAR,"RJ 19 1234","RED");
        Customer customer = new Customer("Akash","Deep","Bangalore","1234567890", CustomerType.GENERAL);
        return new ParkRequest(customer,vehicle,5000,false);
    }
}
