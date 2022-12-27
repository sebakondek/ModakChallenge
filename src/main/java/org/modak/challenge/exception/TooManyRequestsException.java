package org.modak.challenge.exception;

import org.springframework.http.HttpStatus;

public class TooManyRequestsException extends ModakChallengeGenericException {
    public TooManyRequestsException(String message) {
        super(message, HttpStatus.TOO_MANY_REQUESTS);
    }
}
