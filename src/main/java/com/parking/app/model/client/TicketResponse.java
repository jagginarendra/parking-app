package com.parking.app.model.client;

import java.util.Date;

public class TicketResponse {


    private Integer vehicleId;

    private long totalAmount;

    private Date startTime;

    private Date endTime;

    private Long hours;

    public TicketResponse(Integer vehicleId, long amount, Date startTime, Date endTime, Long hours) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalAmount = amount;
        this.vehicleId = vehicleId;
        this.hours = hours;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Long getHours() {
        return hours;
    }

    @Override
    public String toString() {
        return "TicketResponse{" +
                "vehicleId=" + vehicleId +
                ", totalAmount=" + totalAmount +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
