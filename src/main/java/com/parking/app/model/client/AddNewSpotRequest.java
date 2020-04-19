package com.parking.app.model.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parking.app.enums.OccupancyStatus;

import java.util.Objects;

public class AddNewSpotRequest {


    private int numberOfSpots;

    private int companyId;

    private OccupancyStatus occupancyStatus;


    @JsonCreator
    public AddNewSpotRequest(@JsonProperty("number_of_spots") int numberOfSpots,
                             @JsonProperty("parking_lot_id") int companyId,
                             @JsonProperty("intital_status") OccupancyStatus occupancyStatus) {
        this.numberOfSpots = numberOfSpots;
        this.companyId = companyId;
        this.occupancyStatus = occupancyStatus;
    }

    public int getNumberOfSpots() {
        return numberOfSpots;
    }

    public void setNumberOfSpots(int numberOfSpots) {
        this.numberOfSpots = numberOfSpots;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public OccupancyStatus getOccupancyStatus() {
        return occupancyStatus;
    }

    public void setOccupancyStatus(OccupancyStatus occupancyStatus) {
        this.occupancyStatus = occupancyStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddNewSpotRequest that = (AddNewSpotRequest) o;
        return numberOfSpots == that.numberOfSpots &&
                companyId == that.companyId &&
                occupancyStatus == that.occupancyStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfSpots, companyId, occupancyStatus);
    }

    @Override
    public String toString() {
        return "AddNewSpotRequest{" +
                "numberOfSpots=" + numberOfSpots +
                ", companyId=" + companyId +
                ", occupancyStatus=" + occupancyStatus +
                '}';
    }
}
