package com.allnote.user.exception;

public class UserWithUsernameAlreadyExistsException extends RuntimeException {

    public UserWithUsernameAlreadyExistsException(String username) {
        super("User with username: " + username + " already exists!");
    }
}
