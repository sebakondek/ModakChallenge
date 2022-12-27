package org.modak.challenge.exception;

public class ValidatorNotImplementedException extends BadRequestException {
    public ValidatorNotImplementedException(String message) {
        super(message);
    }
}
