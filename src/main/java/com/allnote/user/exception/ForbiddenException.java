package com.allnote.user.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("Forbidden access!");
    }
}
