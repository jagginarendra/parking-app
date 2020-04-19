package com.parking.app.model.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parking.app.enums.VehicleType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class TicketRequest {

    @NotEmpty(message = "specify registration number")
    private String registrationNumber;

    @NotNull(message = "specify vehicle type")
    private VehicleType  vehicleType;

    private Integer parkId;

    @JsonCreator
    public TicketRequest(@JsonProperty("vehicle_type") VehicleType vehicleType,
                         @JsonProperty("registration_number") String registrationNumber,
                         @JsonProperty("parking_id") Integer parkId) {
        this.registrationNumber = registrationNumber;
        this.vehicleType = vehicleType;
        this.parkId = parkId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public Integer getParkId() {
        return parkId;
    }

    public void setParkId(Integer parkId) {
        this.parkId = parkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketRequest that = (TicketRequest) o;
        return Objects.equals(registrationNumber, that.registrationNumber) &&
                vehicleType == that.vehicleType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber, vehicleType);
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public String toString() {
        return "TicketRequest{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", vehicleType=" + vehicleType +
                '}';
    }
}
