package com.parking.app.controller;


import com.parking.app.enums.ReasonCode;
import com.parking.app.exception.*;
import com.parking.app.model.client.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class AbstractController implements Controller {

    //TODO exception handler


    public Response<ParkResponse> park(ParkRequest parkRequest) {
        return null;
    }

    public Response<TicketResponse> unpark(TicketRequest ticketRequest) {
        return null;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<?> handleMethodArgumentNotValidException( MethodArgumentNotValidException ex ) {
        BindingResult bindingResult = ex.getBindingResult();
        final Response<Object> failure = Response.failure(new Response.Error(ReasonCode.INTERNAL_APPLICATION_ERROR, bindingResult.toString()));
        return failure;
    }

    @ExceptionHandler(InvalidConfigException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<?> handleInvalidConfigException(InvalidConfigException ex) {
        final Response<Object> failure = Response.failure(new Response.Error(ReasonCode.INTERNAL_APPLICATION_ERROR, ex.getMessage()));
        return failure;
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<?> handleInvalidRequestException(InvalidRequestException ex) {
        final Response<Object> failure = Response.failure(new Response.Error(ReasonCode.BAD_REQUEST, ex.getMessage()));
        return failure;
    }

    @ExceptionHandler(NoFloorsPresentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<?> handleNoFloorsPresentException(NoFloorsPresentException ex) {
        final Response<Object> failure = Response.failure(new Response.Error(ReasonCode.RESOURCE_NOT_FOUND, ex.getMessage()));
        return failure;
    }

    @ExceptionHandler(NoSpotAvilableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response<?> handleNoSpotAvilableException(NoSpotAvilableException ex) {
        final Response<Object> failure = Response.failure(new Response.Error(ReasonCode.SPOT_NOT_FOUND, ex.getMessage()));
        return failure;
    }

    @ExceptionHandler(UnrecognizedCustomerTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<?> handleUnrecognizedCustomerTypeException(UnrecognizedCustomerTypeException ex) {
        final Response<Object> failure = Response.failure(new Response.Error(ReasonCode.BAD_REQUEST, ex.getMessage()));
        return failure;
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Response<?> handleVehicleNotFOundException(VehicleNotFoundException ex) {
        final Response<Object> failure = Response.failure(new Response.Error(ReasonCode.VEHICLE_NOT_FOUND, ex.getMessage()));
        return failure;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<?> handleAllExceptions(Exception ex) {
        final Response<Object> failure = Response.failure(
                new Response.Error(ReasonCode.INTERNAL_APPLICATION_ERROR, ex.getLocalizedMessage()== null ? ex.getMessage() : ex.getLocalizedMessage()  ));
        return failure;
    }


}
