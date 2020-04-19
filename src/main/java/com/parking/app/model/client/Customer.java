package com.parking.app.model.client;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parking.app.enums.CustomerType;

import javax.validation.constraints.NotEmpty;

public class Customer {

    private int customer_Id;

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

    @NotEmpty(message = "specify customer type")
    private CustomerType customerType;

    @JsonCreator
    public Customer(@JsonProperty("first_name") String firstName,
                    @JsonProperty("last_name")String lastName,
                    @JsonProperty("address")String address,
                    @JsonProperty("phone")String phone,
                    @JsonProperty("customer_type")CustomerType customerType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.customerType = customerType;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public int getCustomer_Id() {
        return customer_Id;
    }

    public void setCustomer_Id(int customer_Id) {
        this.customer_Id = customer_Id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", customerType=" + customerType +
                '}';
    }
}
