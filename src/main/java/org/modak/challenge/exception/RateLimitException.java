package org.modak.challenge.exception;

public class RateLimitException extends TooManyRequestsException {
    public RateLimitException(String message) {
        super(message);
    }
}
