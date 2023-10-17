package com.allnote.auth;

import com.allnote.auth.dto.AuthenticationResponse;
import com.allnote.auth.dto.LoginRequest;
import com.allnote.user.dto.PostUserRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface TokenController {

    @PostMapping("/api/auth/token")
    @ResponseStatus(HttpStatus.OK)
    AuthenticationResponse token(@RequestBody LoginRequest request);

    @PostMapping("/api/auth/register")
    @ResponseStatus(HttpStatus.OK)
    AuthenticationResponse register(@RequestBody PostUserRequest request);

    @PostMapping("/api/auth/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    AuthenticationResponse refreshToken(HttpServletRequest request);

}
