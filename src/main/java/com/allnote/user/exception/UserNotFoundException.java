package com.allnote.user.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(long id) {
        super("user with id: " + id + " not found!");
    }
}
