package com.parking.app.exception;

public class NoMatchingSpotFound extends RuntimeException {
    public NoMatchingSpotFound(String message) {
        super(message);
    }
}
