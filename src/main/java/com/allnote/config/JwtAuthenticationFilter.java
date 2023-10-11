package com.allnote.config;

import com.allnote.auth.TokenService;
import com.allnote.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {
        log.info(request.getServletPath());
        if (request.getServletPath().startsWith("/api/auth")) {
            log.info("path for auth");
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.info("authHeader == null or authHeader doesn't start with Bearer");
            filterChain.doFilter(request, response);
            return;
        }
        final String jwt = authHeader.substring(7);
        final String username = tokenService.extractUsername(jwt);
        if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            log.info("username == null or user authenticated");
            log.info(SecurityContextHolder.getContext().getAuthentication());
            filterChain.doFilter(request, response);
            return;
        }
        UserDetails userDetails = userService.loadUserByUsername(username);
        if (!tokenService.isTokenValid(jwt, userDetails)) {
            log.info("invalid token");
            filterChain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
