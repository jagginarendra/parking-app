package com.parking.app.model.jpa;

import com.parking.app.enums.VehicleType;

public class MotorCycleDTO extends VehicleDTO {


    public MotorCycleDTO(String registrationNumber, String color, CustomerDTO customerDTO) {
        super(VehicleType.MOTORCYCLE, registrationNumber, color, customerDTO);
    }

    public MotorCycleDTO(String registrationNumber, String color) {
        super(VehicleType.MOTORCYCLE, registrationNumber, color);
    }
}
