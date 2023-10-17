package com.allnote.auth;

import com.allnote.auth.dto.AuthenticationResponse;
import com.allnote.auth.dto.LoginRequest;
import com.allnote.user.dto.PostUserRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenControllerDefault implements TokenController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Override
    public AuthenticationResponse token(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.username(), request.password()));
        return tokenService.authenticate(request);
    }

    @Override
    public AuthenticationResponse register(PostUserRequest request) {
        return tokenService.register(request);
    }

    @Override
    public AuthenticationResponse refreshToken(HttpServletRequest request) {
        return tokenService.refresh(request);
    }

}
