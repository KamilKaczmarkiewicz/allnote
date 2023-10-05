package com.allnote.user.dto;

import com.allnote.user.User;

import java.time.LocalDate;

public record PutUserRequest(
        String firstName,
        String lastName,
        String email,
        String username,
        String password,
        LocalDate birthDate) {

    public User putUserRequestToUser(User user) {
        if (firstName != null)
            user.setFirstName(firstName);
        if (lastName != null)
            user.setLastName(lastName);
        if (email != null)
            user.setEmail(email);
        if (username != null)
            user.setUsername(username);
        if (password != null)
            user.setPassword(password);
        if (birthDate != null)
            user.setBirthDate(birthDate);
        return user;
    }
}
