package com.parking.app.utils;

import com.parking.app.enums.VehicleType;
import com.parking.app.model.client.Customer;
import com.parking.app.model.client.Vehicle;
import com.parking.app.model.jpa.*;

import java.util.concurrent.TimeUnit;

public class ParkUtil {

    public static VehicleDTO getVehicleDetails(Vehicle vehicle, Customer customer) {
        return vehicle.getVehicleType() == VehicleType.CAR ?
                new CarDTO(vehicle.getRegistrationNumber(), vehicle.getColor(), getCustomer(customer)) :
                new MotorCycleDTO(vehicle.getRegistrationNumber(), vehicle.getColor(), getCustomer(customer));
    }

   /* public static VehicleDTO getVehicleDetails(Vehicle vehicle) {
        return vehicle.getVehicleType() == VehicleType.CAR ?
                new CarDTO(vehicle.getRegistrationNumber(), vehicle.getColor()) :
                new MotorCycleDTO(vehicle.getRegistrationNumber(), vehicle.getColor());
    }*/


    public static VehicleDTO getVehicleDetails(Vehicle vehicle) {
        return new VehicleDTO(vehicle.getVehicleType(), vehicle.getRegistrationNumber(), vehicle.getColor());
    }

    public static CustomerDTO getCustomer(Customer customer) {
        return new CustomerDTO(customer.getFirstName() , customer.getLastName(), customer.getAddress(),customer.getPhone(),customer.getCustomerType());
    }

    public static long getCharges(ParkDTO parkDTO, String parkingCharges) {
        long diff = parkDTO.getEndTime().getTime() - parkDTO.getStartTime().getTime();
        long hours = TimeUnit.MILLISECONDS.toHours(diff);
        if(parkDTO.getVehicleDTO().getVehicleType() == VehicleType.CAR){
            return hours > 1 ? hours * 25 : 30;
        }else{
            return hours > 1 ? hours  *  15  : 20;
        }
    }


}
