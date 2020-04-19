package com.parking.app.enums;

public enum VehicleType {

    MOTORCYCLE(1), CAR(1), BUS(2), TRUCK(3);

    private int spotSize;


    VehicleType(int spotSize) {
        this.spotSize = spotSize;
    }

    public int getSpotSize() {
        return spotSize;
    }
}
