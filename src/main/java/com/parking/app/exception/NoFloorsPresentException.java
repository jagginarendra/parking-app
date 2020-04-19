package com.parking.app.exception;

public class NoFloorsPresentException extends RuntimeException {

    public NoFloorsPresentException(String message) {
        super(message);
    }
}
