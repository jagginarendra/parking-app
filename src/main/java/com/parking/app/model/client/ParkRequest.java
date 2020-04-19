package com.parking.app.model.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class ParkRequest {

    @NotNull(message = "specify customer details")
    private final Customer customer;

    @NotNull(message = "specify vehicle details")
    private final Vehicle vehicle;

    @NotNull(message="specify parkingLotId")
    private final int parkingLotID;

    private final boolean isCarPooled;

    @JsonCreator
    public ParkRequest(@JsonProperty("customer") Customer customer,
                       @JsonProperty("vehicle") Vehicle vehicle,
                       @JsonProperty("parking_lot_id") int parkingLotID,
                       @JsonProperty("isCarPooled") boolean isCarPooled) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.parkingLotID = parkingLotID;
        this.isCarPooled = isCarPooled;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getParkingLotID() {
        return parkingLotID;
    }

    public boolean isCarPooled() {
        return isCarPooled;
    }

    @Override
    public String toString() {
        return "ParkRequest{" +
                "customer=" + customer +
                ", vehicle=" + vehicle +
                ", parkingLotID=" + parkingLotID +
                ", isCarPooled=" + isCarPooled +
                '}';
    }


}
