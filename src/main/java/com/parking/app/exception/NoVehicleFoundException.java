package com.parking.app.exception;

public class NoVehicleFoundException extends RuntimeException {

    public NoVehicleFoundException(String message) {
        super(message);
    }
}
