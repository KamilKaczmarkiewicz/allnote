package com.allnote.user.exception;

public class IncorrectCredentialsException extends RuntimeException {

    public IncorrectCredentialsException() {
        super("Incorrect login or password!");
    }
}
