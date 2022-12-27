package org.modak.challenge.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(value = ModakChallengeGenericException.class)
    protected ResponseEntity<ErrorResponse> handleError(ModakChallengeGenericException ex) {
        logger.error(ex.getMessage(), ex);

        ErrorResponse response = new ErrorResponse(ex.getMessage(), ex.getHttpStatus());

        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    /*
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String errorMessage = Objects.nonNull(ex.getBindingResult().getFieldError()) ?
                ex.getBindingResult().getFieldError().getDefaultMessage() :
                "";

        logger.warn(errorMessage);

        ErrorResponse response = new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

     */

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleError(ConstraintViolationException ex) {
        logger.warn(ex.getMessage());

        ErrorResponse response = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleError(Exception ex) {
        logger.error(ex.getMessage(), ex);

        ErrorResponse response = new ErrorResponse("Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
