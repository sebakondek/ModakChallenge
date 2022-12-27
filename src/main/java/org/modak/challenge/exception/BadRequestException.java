package org.modak.challenge.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ModakChallengeGenericException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
