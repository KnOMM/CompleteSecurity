package org.backend.oauth2withjwt.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomUserAlreadyExistsException
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict() {
        String bodyOfResponse = "Error from controller advice";
        return new ResponseEntity<>(bodyOfResponse,
                new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }
}
