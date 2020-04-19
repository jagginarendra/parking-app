package com.parking.app.exception;

public class NoMatchingParkingFoundException extends RuntimeException{
    public NoMatchingParkingFoundException(String message) {
        super(message);
    }
}
