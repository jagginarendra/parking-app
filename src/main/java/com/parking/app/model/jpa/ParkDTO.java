package com.parking.app.model.jpa;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "park")
public class ParkDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "park_id")
    private Integer parkingId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    private Date startTime;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time", insertable = false, updatable = true)
    private Date endTime;

    @Column(name = "spot_id")
    private int spotId;

    @Column(name = "parking_lot_id")
    private int parkingLotId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id")
    private VehicleDTO vehicleDTO;

    @Column(name = "amount", insertable = false, updatable = true)
    private Long amount;


    public ParkDTO(int spotId, int parkingLotId, VehicleDTO vehicleDTO) {
        this.spotId = spotId;
        this.parkingLotId = parkingLotId;
        this.vehicleDTO = vehicleDTO;
    }

    public ParkDTO() {
    }

    public VehicleDTO getVehicleDTO() {
        return vehicleDTO;
    }

    public Integer getParkingId() {
        return parkingId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getSpotId() {
        return spotId;
    }

    public int getParkingLotId() {
        return parkingLotId;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    public void setParkingLotId(int parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public void setVehicleDTO(VehicleDTO vehicleDTO) {
        this.vehicleDTO = vehicleDTO;
    }


    public Long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ParkDTO{" +
                "parkinId=" + parkingId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", spotId=" + spotId +
                ", parkingLotId=" + parkingLotId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkDTO parkDTO = (ParkDTO) o;
        return parkingId == parkDTO.parkingId &&
                spotId == parkDTO.spotId &&
                parkingLotId == parkDTO.parkingLotId &&
                Objects.equals(startTime, parkDTO.startTime) &&
                Objects.equals(endTime, parkDTO.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parkingId, startTime, endTime, spotId, parkingLotId);
    }
}
