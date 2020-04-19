package com.parking.app.exception;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(String message) {
        super(message);
    }
}
