package com.allnote.user.exception;

public class PasswordsMustBeTheSameException extends RuntimeException {

    public PasswordsMustBeTheSameException() {
        super("Passwords dose not match!");
    }
}
