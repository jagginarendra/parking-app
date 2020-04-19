package com.parking.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.app.enums.VehicleType;
import com.parking.app.service.*;
import com.parking.app.service.admin.ParkingSpotService;
import com.parking.app.service.admin.impl.ParkingSpotServiceImpl;
import com.parking.app.service.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ParkConfig {

    @Bean
    public ParkService parkService() {
        return new ParkServiceImpl();
    }

    @Bean
    public ParkingSpotService parkingSpotService() {
        return new ParkingSpotServiceImpl();
    }

    @Bean
    public VehicleParkingService carParkService() {
        return new CarParkServiceImpl();
    }

    @Bean
    public VehicleParkingService motorCycleParkService() {
        return new MotorCycleParkServiceImpl();
    }

    @Bean
    public Map<VehicleType, VehicleParkingService> parkServiceHashMap(VehicleParkingService motorCycleParkService, VehicleParkingService carParkService) {
        Map<VehicleType, VehicleParkingService> vehicleServiceMap = new HashMap<>();
        vehicleServiceMap.put(VehicleType.CAR, carParkService);
        vehicleServiceMap.put(VehicleType.MOTORCYCLE, motorCycleParkService);
        return vehicleServiceMap;
    }

    @Bean
    public RetrieveParkingSpots retrieveParkingSpots() {
        return new RetrieveParkingSpotsImpl();
    }

    @Bean
    public RateRuleService rateRuleService() {
        return new RateRuleServiceImpl();
    }

    @Bean
    public AssignSpotService slotService() {
        return new AssignAssignSpotServiceImpl();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

}
