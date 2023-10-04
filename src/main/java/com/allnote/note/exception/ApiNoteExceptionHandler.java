package com.allnote.note.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiNoteExceptionHandler {

    @ExceptionHandler(value = {NoteNotFoundException.class})
    public ResponseEntity<Object> handleNoteNotFoundException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
    }
}
