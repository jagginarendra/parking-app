package com.parking.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.parking.app.dao.*;
import com.parking.app.service.ParkService;
import com.parking.app.service.RateRuleService;
import com.parking.app.service.admin.ParkingSpotService;
import com.parking.app.service.admin.impl.ParkingSpotServiceImpl;
import com.parking.app.service.impl.ParkServiceImpl;
import com.parking.app.service.impl.RateRuleServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
    Defines All configs at one place for ease of managing
 */
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
    public RetrieveParkingSpotsDAO retrieveParkingSpots() {
        return new RetrieveParkingSpotsDAOImpl();
    }

    @Bean
    public RateRuleService rateRuleService() {
        return new RateRuleServiceImpl();
    }

    @Bean
    public AssignSpotDAO slotService() {
        return new AssignSpotDAOImpl();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new Jdk8Module());
    }

    @Bean
    public AvailabilityDAO availabilityDAO() {
        return new AvailabilityDAOImpl();
    }

    @Bean
    public ReleaseParkingDAO releaseParkingDAO() {
        return new ReleaseParkingDAOImpl();
    }

    @Bean
    public VehicleDAO vehicleDAO() {
        return new VehicleDAOImpl();
    }

}
