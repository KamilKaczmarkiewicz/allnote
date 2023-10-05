package com.allnote.user.dto;

import com.allnote.user.User;

import java.time.LocalDate;

public record PostUserRequest(
        String firstName,
        String lastName,
        String email,
        String username,
        String password,
        LocalDate birthDate) {

    public User postUserRequestToUser() {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .username(username)
                .password(password)
                .birthDate(birthDate)
                .build();
    }
}
