package com.parking.app.model.jpa;

import com.parking.app.enums.VehicleType;

public class CarDTO extends VehicleDTO {


    public CarDTO(String registrationNumber, String color, CustomerDTO customerDTO) {
        super(VehicleType.CAR, registrationNumber, color, customerDTO);
    }
}
