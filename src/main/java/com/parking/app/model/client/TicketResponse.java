package com.parking.app.model.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TicketResponse {


    private Integer vehicleId;

    private long totalAmount;

    private String startTime;

    private String endTime;

    private Long hours;

    @JsonCreator
    public TicketResponse(@JsonProperty("vehicle_Id") Integer vehicleId,
                          @JsonProperty("total_Amount") long totalAmount,
                          @JsonProperty("start_time") String startTime,
                          @JsonProperty("end_time") String endTime,
                          @JsonProperty("hours") Long hours) {
        this.vehicleId = vehicleId;
        this.totalAmount = totalAmount;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hours = hours;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "TicketResponse{" +
                "vehicleId=" + vehicleId +
                ", totalAmount=" + totalAmount +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", hours=" + hours +
                '}';
    }
}
