package com.parking.app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.app.exception.InvalidConfigException;
import com.parking.app.model.jpa.ParkDTO;
import com.parking.app.model.jpa.Recipt;
import com.parking.app.service.RateRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class RateRuleServiceImpl implements RateRuleService {


    @Autowired
    ObjectMapper objectMapper;

    //Parking building based config for vehicles hourly charges
    @Value("${parking.charges.hourly.by.parking}")
    String parkingRatesJson;

    @Override
    public Recipt getRateDetails(ParkDTO parkDTO) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        long duration = parkDTO.getEndTime().getTime() - parkDTO.getStartTime().getTime();
        long hours = TimeUnit.MILLISECONDS.toHours(duration) < 1 ? 1 : TimeUnit.MILLISECONDS.toHours(duration);

        Recipt recipt = new Recipt.ReciptBuilder().
                setAmount(calculateAmount(hours)).
                setDuration(hours).
                setStartDate(formatter.format(parkDTO.getStartTime())).
                setEndDate(formatter.format(parkDTO.getEndTime())).build();

        return recipt;
    }

    //TODO Rate rule for building wise
    private Long calculateAmount(long hours) {
        try {
            HashMap hashMap = objectMapper.readValue(parkingRatesJson, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new InvalidConfigException("Parking rates config not defined properly");
        }
        return 100 * hours;
    }

}
