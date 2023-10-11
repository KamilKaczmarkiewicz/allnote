package com.allnote.auth.dto;

public record LoginRequest(
        String username,
        String password
) {
}
