package com.parking.app.model.client;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parking.app.enums.VehicleType;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class Vehicle {

    private int vehicleId;

    @NotEmpty(message = "specify vehivle type details")
    private final VehicleType vehicleType;

    @NotEmpty(message = "specify vehicle registration details")
    private final String registrationNumber;

    @NotEmpty(message = "specify vehicle color")
    private final String color;

    private final int spotsRequired;

    @JsonCreator
    public Vehicle(@JsonProperty("vehicle_type") VehicleType vehicleType,
                   @JsonProperty("registration_number") String registrationNumber,
                   @JsonProperty("color") String color) {
        this.vehicleType = vehicleType;
        this.registrationNumber = registrationNumber;
        this.color = color;
        this.spotsRequired = vehicleType.getSpotSize();
    }


    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getColor() {
        return color;
    }

    public int getSpotsRequired() {
        return spotsRequired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return vehicleId == vehicle.vehicleId &&
                spotsRequired == vehicle.spotsRequired &&
                vehicleType == vehicle.vehicleType &&
                Objects.equals(registrationNumber, vehicle.registrationNumber) &&
                Objects.equals(color, vehicle.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleId, vehicleType, registrationNumber, color, spotsRequired);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId=" + vehicleId +
                ", vehicleType=" + vehicleType +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", color='" + color + '\'' +
                ", spotsRequired=" + spotsRequired +
                '}';
    }
}
