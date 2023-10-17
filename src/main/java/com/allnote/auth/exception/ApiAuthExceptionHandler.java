package com.allnote.auth.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiAuthExceptionHandler {

    @ExceptionHandler(value = {InvalidRefreshTokenException.class})
    public ResponseEntity<Object> handleInvalidRefreshTokenException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
    }

}
