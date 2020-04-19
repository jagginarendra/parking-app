package com.parking.app.enums;

public enum OccupancyStatus {

    FULL("F"), PARTIAL("P"), EMPTY("E");

    private String status;

    OccupancyStatus(String status){
        this.status = status;
    }

    public String toString() {
        return status;
    }
}
