package com.allnote.user.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiUserExceptionHandler {

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(value = {InvalidFileExtensionException.class})
    public ResponseEntity<Object> handleInvalidFileExtensionException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(value = {UserWithUsernameAlreadyExistsException.class})
    public ResponseEntity<Object> handleUserWithUsernameAlreadyExistsException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<Object> handleForbiddenException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(403));
    }

}
