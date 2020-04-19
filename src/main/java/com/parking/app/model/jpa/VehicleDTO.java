package com.parking.app.model.jpa;


import com.parking.app.enums.VehicleType;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "vehicle")
public class VehicleDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vehicle_id")
    private Integer vehicleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type")
    private VehicleType vehicleType;

    @Column(name = "registration_number")
    private String registrationNumber;

    private String color;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private CustomerDTO customerDTO;

    //@Transient
    // private int spotsRequired;


    public VehicleDTO(VehicleType vehicleType, String registrationNumber, String color, CustomerDTO customerDTO) {
        this.vehicleType = vehicleType;
        this.registrationNumber = registrationNumber;
        this.color = color;
       // this.spotsRequired = spotsRequired;
        this.customerDTO = customerDTO;
    }

    public VehicleDTO(VehicleType vehicleType, String registrationNumber, String color) {
        this.vehicleType = vehicleType;
        this.registrationNumber = registrationNumber;
        this.color = color;
    }

    protected VehicleDTO() {
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public Integer getVehicleId() {
        return vehicleId;
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

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleDTO vehicleDTO = (VehicleDTO) o;
        return vehicleId == vehicleDTO.vehicleId &&
                //spotsRequired == vehicleDTO.spotsRequired &&
                vehicleType == vehicleDTO.vehicleType &&
                Objects.equals(registrationNumber, vehicleDTO.registrationNumber) &&
                Objects.equals(color, vehicleDTO.color) &&
                Objects.equals(customerDTO, vehicleDTO.customerDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleId, vehicleType, registrationNumber, color, customerDTO);
    }

    @Override
    public String toString() {
        return "VehicleDTO{" +
                "vehicleId=" + vehicleId +
                ", vehicleType=" + vehicleType +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", color='" + color + '\'' +
                ", customerDTO=" + customerDTO +
                '}';
    }
}
