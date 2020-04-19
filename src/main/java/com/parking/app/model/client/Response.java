package com.parking.app.model.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parking.app.enums.ReasonCode;
import com.parking.app.enums.TransactionStatus;

import java.util.Optional;

public class Response<T> {

    private Optional<T> body;
    private boolean success;
    private TransactionStatus transactionStatus;
    private Optional<Error> error;

    @JsonCreator
    public Response(@JsonProperty("success") boolean success,@JsonProperty("body") T body, Error error,
                    @JsonProperty("transaction") TransactionStatus transactionStatus) {
        this.body = Optional.ofNullable(body);
        this.success = success;
        this.error = Optional.ofNullable(error);
        this.transactionStatus = transactionStatus;
    }

    public Optional<T> getBody() {
        return body;
    }

    public static <T> Response<T> success(T body) {
        return new Response<T>(true, body, null, TransactionStatus.COMPLETED);
    }

    private static TransactionStatus getTransactionStatus(Error error) {
        return Optional.ofNullable(error).map(Error::getCode).filter(Response::isSystemError).map(e -> TransactionStatus.UNKNOWN).orElse(TransactionStatus.FAILED);
    }

    private static boolean isSystemError(ReasonCode reasonCode) {
        return ReasonCode.SYSTEM_ERROR == reasonCode;
    }

    public static <T> Response<T> failure(Error error) {
        return new Response<T>(false, null, error, getTransactionStatus(error));
    }

    public static class Error {
        private final ReasonCode code;
        private final String message;

        public Error(ReasonCode code, String message) {
            this.code = code;
            this.message = message;
        }

        public ReasonCode getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    public Optional<Error> getError() {
        return error;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "Response{" +
                ", success=" + success +
                ", transactionStatus=" + transactionStatus +
                ", error=" + error +
                '}';
    }

}
