package com.parking.app.model.jpa;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "spots")
public class ParkingSpotDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "spot_id")
    private int spotId;

    @Column(name = "floor_id")
    private int floorId;

    //1 for bottom 2 for upper
    private int level;

    @Column(name = "status")
    private String occupancyStatus;

    @Column(name = "count")
    private int occupancyCount;

    @Column(name = "company_id")
    private int companyId;


    public ParkingSpotDTO(int floorId, int level, String occupancyStatus, int occupancyCount, int companyId) {
        this.floorId = floorId;
        this.level = level;
        this.occupancyStatus = occupancyStatus;
        this.occupancyCount = occupancyCount;
        this.companyId = companyId;
    }

    protected ParkingSpotDTO() {
    }

    public int getSpotId() {
        return spotId;
    }

    public int getFloorId() {
        return floorId;
    }

    public int getLevel() {
        return level;
    }

    public String getOccupancyStatus() {
        return occupancyStatus;
    }

    public int getOccupancyCount() {
        return occupancyCount;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setOccupancyStatus(String occupancyStatus) {
        this.occupancyStatus = occupancyStatus;
    }

    public void setOccupancyCount(int occupancyCount) {
        this.occupancyCount = occupancyCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpotDTO that = (ParkingSpotDTO) o;
        return spotId == that.spotId &&
                floorId == that.floorId &&
                level == that.level &&
                occupancyCount == that.occupancyCount &&
                companyId == that.companyId &&
                occupancyStatus == that.occupancyStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(spotId, floorId, level, occupancyStatus, occupancyCount, companyId);
    }


    @Override
    public String toString() {
        return "ParkingSpotDTO{" +
                "spotId=" + spotId +
                ", floorId=" + floorId +
                ", level=" + level +
                ", occupancyStatus=" + occupancyStatus +
                ", occupancyCount=" + occupancyCount +
                ", companyId=" + companyId +
                '}';
    }
}
