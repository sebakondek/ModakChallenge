package org.modak.challenge.exception;

import org.springframework.http.HttpStatus;

import java.util.Objects;

public class ErrorResponse {
    private final String message;
    private final HttpStatus httpStatus;

    public ErrorResponse(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(message, that.message) && httpStatus == that.httpStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, httpStatus);
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "message='" + message + '\'' +
                ", httpCode=" + httpStatus +
                '}';
    }
}
