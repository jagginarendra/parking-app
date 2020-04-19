package com.parking.app.model.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class TicketRequest {

    @NotEmpty(message = "specify registration number")
    private String registrationNumber;

    private Integer  vehicleId;

    private Integer parkId;

    @JsonCreator
    public TicketRequest(@JsonProperty("vehicle_id") Integer vehicleId,
                         @JsonProperty("registration_number") String registrationNumber,
                         @JsonProperty("parking_id") Integer parkId) {
        this.registrationNumber = registrationNumber;
        this.vehicleId = vehicleId;
        this.parkId = parkId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getParkId() {
        return parkId;
    }

    public void setParkId(Integer parkId) {
        this.parkId = parkId;
    }
}
