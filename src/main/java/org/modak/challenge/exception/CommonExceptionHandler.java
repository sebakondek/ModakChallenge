package org.modak.challenge.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@Slf4j
@ControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ModakChallengeGenericException.class)
    protected ResponseEntity<ErrorResponse> handleError(ModakChallengeGenericException ex) {
        log.error(ex.getMessage(), ex);

        ErrorResponse response = new ErrorResponse(ex.getMessage(), ex.getHttpStatus());

        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errorMessage = Objects.nonNull(ex.getBindingResult().getFieldError()) ?
                ex.getBindingResult().getFieldError().getDefaultMessage() :
                "";

        log.warn(errorMessage);

        ErrorResponse response = new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<?> handleError(TooManyRequestsException ex) {
        log.warn(ex.getMessage());

        ErrorResponse response = new ErrorResponse(ex.getMessage(), HttpStatus.TOO_MANY_REQUESTS);

        return new ResponseEntity<>(response, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleError(Exception ex) {
        log.error(ex.getMessage(), ex);

        ErrorResponse response = new ErrorResponse("Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
