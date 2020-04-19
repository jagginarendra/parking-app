package com.parking.app.model.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ParkResponse {

    private Integer spotId;

    private String startTime;

    private Integer vehicleId;

    @JsonCreator
    public ParkResponse(@JsonProperty("parking_spot_Id")  Integer spotId,
                        @JsonProperty("start_time") String startTime,
                        @JsonProperty("vehicle_Id") Integer vehicleId) {
        this.spotId = spotId;
        this.startTime = startTime;
        this.vehicleId = vehicleId;
    }

    public Integer getSpotId() {
        return spotId;
    }

    public String getStartTime() {
        return startTime;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    @Override
    public String toString() {
        return "ParkResponse{" +
                "spotId=" + spotId +
                ", startTime=" + startTime +
                ", vehicleId=" + vehicleId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkResponse that = (ParkResponse) o;
        return Objects.equals(spotId, that.spotId) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(vehicleId, that.vehicleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spotId, startTime, vehicleId);
    }
}
