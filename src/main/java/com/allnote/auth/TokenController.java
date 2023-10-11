package com.allnote.auth;

import com.allnote.auth.dto.AuthenticationResponse;
import com.allnote.auth.dto.LoginRequest;
import com.allnote.user.dto.PostUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/api/auth/token")
    AuthenticationResponse token(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.username(), request.password()));
        return AuthenticationResponse.builder()
                .accessToken(tokenService.authenticate(request))
                .build();
    }

    @PostMapping("/api/auth/register")
    AuthenticationResponse register(@RequestBody PostUserRequest request) {
        return AuthenticationResponse.builder()
                .accessToken(tokenService.register(request))
                .build();
    }


}
