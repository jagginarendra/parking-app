package com.parking.app.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.app.enums.VehicleType;
import com.parking.app.exception.InvalidConfigException;
import com.parking.app.model.client.Customer;
import com.parking.app.model.client.ParkRequest;
import com.parking.app.model.client.Vehicle;
import com.parking.app.model.jpa.CarDTO;
import com.parking.app.model.jpa.CustomerDTO;
import com.parking.app.model.jpa.MotorCycleDTO;
import com.parking.app.model.jpa.VehicleDTO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkUtil {

    public static VehicleDTO getVehicleDetails(Vehicle vehicle, Customer customer) {
        return vehicle.getVehicleType() == VehicleType.CAR ?
                new CarDTO(vehicle.getRegistrationNumber(), vehicle.getColor(), getCustomer(customer)) :
                new MotorCycleDTO(vehicle.getRegistrationNumber(), vehicle.getColor(), getCustomer(customer));
    }

    public static VehicleDTO getVehicleDetails(Vehicle vehicle) {
        return new VehicleDTO(vehicle.getVehicleType(), vehicle.getRegistrationNumber(), vehicle.getColor());
    }

    public static CustomerDTO getCustomer(Customer customer) {
        return new CustomerDTO(customer.getFirstName() , customer.getLastName(), customer.getAddress(),customer.getPhone(),customer.getCustomerType());
    }

    public static List<String> getOccupancyStatus(ParkRequest parkRequest, String occupanyConfig, ObjectMapper objectMapper) {
        try {
            Map<String,String> vehicleOccupancyMap = objectMapper.readValue(occupanyConfig, HashMap.class);
            if(!vehicleOccupancyMap.containsKey(parkRequest.getVehicle().getVehicleType().name())){
                throw new InvalidConfigException("Invalid configuration for vehicle-occupancy");
            }
            String status = vehicleOccupancyMap.get(parkRequest.getVehicle().getVehicleType().name());
            return Arrays.asList(status.split(","));
        } catch (JsonProcessingException e) {
            throw new InvalidConfigException("Invalid configuration for vehicle-occupancy");
        }
    }


}
