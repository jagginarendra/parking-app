package com.parking.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.parking.app.dao.*;
import com.parking.app.service.ParkService;
import com.parking.app.service.RateRuleService;
import com.parking.app.service.SpotService;
import com.parking.app.service.VehicleService;
import com.parking.app.service.admin.AdminParkingSpotService;
import com.parking.app.service.admin.impl.AdminAdminParkingSpotServiceImpl;
import com.parking.app.service.impl.ParkServiceImpl;
import com.parking.app.service.impl.RateRuleServiceImpl;
import com.parking.app.service.impl.SpotServiceImpl;
import com.parking.app.service.impl.VehicleServiceImpl;
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
    public AdminParkingSpotService parkingSpotService() {
        return new AdminAdminParkingSpotServiceImpl();
    }

    @Bean
    public RateRuleService rateRuleService() {
        return new RateRuleServiceImpl();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new Jdk8Module());
    }


    @Bean
    public VehicleDAO vehicleDAO() {
        return new VehicleDAOImpl();
    }

    @Bean
    public SpotDAO spotDAO(){
        return new SpotDAOImpl();
    }

    @Bean
    public ParkDAO parkDAO(){
        return new ParkDAOImpl();
    }


    @Bean
    public SpotService spotService(){
        return new SpotServiceImpl();
    }

    @Bean
    public VehicleService vehicleService(){
        return new VehicleServiceImpl();
    }



}
