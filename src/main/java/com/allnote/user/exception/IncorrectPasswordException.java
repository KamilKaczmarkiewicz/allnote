package com.allnote.user.exception;

public class IncorrectPasswordException extends RuntimeException {

    public IncorrectPasswordException() {
        super("Incorrect password!");
    }
}
