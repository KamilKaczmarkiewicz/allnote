package com.allnote.user.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(long userId) {
        super("user with id: " + userId + " not found!");
    }

    public UserNotFoundException() {
        super("user not found!");
    }
}
