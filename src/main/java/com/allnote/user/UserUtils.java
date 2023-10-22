package com.allnote.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUtils {

    private static final String ROLE_PREFIX = "ROLE_";

    public static boolean isUserAdminOrUserCaller(long userId) {
        return isUserCaller(userId) || isUserAdmin();
    }

    public static boolean isUserAdmin() {
        return hasRole(Role.ADMIN);
    }

    public static boolean isUserCaller(long userId) {
        User user = getUserFromPrincipal();
        return user.getId() == userId;
    }

    private static User getUserFromPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean hasRole(Role role) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .anyMatch(authority -> (ROLE_PREFIX + role.name()).equals(authority));
    }

    public static boolean hasPermission(Permission permission) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .anyMatch(authority -> permission.getPermission().equals(authority));
    }

    public static String generateRandomPassword() {
        return RandomStringUtils.random(12, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789!@#$|");
    }

}
