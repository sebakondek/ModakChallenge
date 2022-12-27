package org.modak.challenge.exception;

public class InvalidEnumException extends BadRequestException {
    public InvalidEnumException(String message) {
        super(message);
    }
}
