package org.modak.challenge.exception;

import org.springframework.http.HttpStatus;

import java.util.Objects;

public class ModakChallengeGenericException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;

    public ModakChallengeGenericException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public ModakChallengeGenericException(String message, HttpStatus httpStatus, Throwable ex) {
        super(message, ex);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
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
        ModakChallengeGenericException that = (ModakChallengeGenericException) o;
        return Objects.equals(message, that.message) && httpStatus == that.httpStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, httpStatus);
    }
}
