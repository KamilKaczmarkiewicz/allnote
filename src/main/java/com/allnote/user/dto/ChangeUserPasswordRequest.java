package com.allnote.user.dto;

public record ChangeUserPasswordRequest(
        String oldPassword,
        String newPassword,
        String newPasswordRepeated
) {
}
