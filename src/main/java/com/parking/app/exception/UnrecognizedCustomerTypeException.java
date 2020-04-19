package com.parking.app.exception;

public class UnrecognizedCustomerTypeException extends RuntimeException {
    public UnrecognizedCustomerTypeException(String message) {
        super(message);
    }
}
