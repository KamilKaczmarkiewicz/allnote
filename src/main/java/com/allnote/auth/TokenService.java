package com.allnote.auth;

import com.allnote.auth.dto.AuthenticationResponse;
import com.allnote.auth.dto.LoginRequest;
import com.allnote.auth.exception.InvalidRefreshTokenException;
import com.allnote.user.User;
import com.allnote.user.UserService;
import com.allnote.user.dto.PostUserRequest;
import com.allnote.user.exception.UserWithUsernameAlreadyExistsException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class TokenService {


    private final UserService userService;
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration-token}")
    private long jwtExpirationToken;
    @Value("${application.security.jwt.expiration-refresh-token}")
    private long jwtExpirationRefreshToken;

    public AuthenticationResponse authenticate(LoginRequest request) {
        UserDetails user = userService.loadUserByUsername(request.username());
        return new AuthenticationResponse(generateToken(user), generateRefreshToken(user));
    }

    public AuthenticationResponse refresh(HttpServletRequest request) {
        final String jwt = getTokenFromHttpServletRequest(request)
                .orElseThrow(() -> new InvalidRefreshTokenException());
        Boolean isRefreshToken = (Boolean) extractClaim(jwt,"refreshToken");
        if(isRefreshToken == null || !isRefreshToken){
            throw new InvalidRefreshTokenException();
        }
        String username = extractUsername(jwt);
        UserDetails user = userService.loadUserByUsername(username);
        if (!isTokenValid(jwt, user)) {
            throw new InvalidRefreshTokenException();
        }
        return new AuthenticationResponse(generateToken(user), generateRefreshToken(user));
    }

    public AuthenticationResponse register(PostUserRequest request) {
        try {
            userService.loadUserByUsername(request.username());
            throw new UserWithUsernameAlreadyExistsException(request.username());
        } catch (UsernameNotFoundException e) {
            log.info("Register new user: " + request.username());
        }
        User user = request.postUserRequestToUser();
        userService.create(user);
        return new AuthenticationResponse(generateToken(user), generateRefreshToken(user));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Object extractClaim(String token, Object key) {
        final Claims claims = extractAllClaims(token);
        return claims.get(key);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        if (userDetails instanceof User){
            User user = (User) userDetails;
            extraClaims.put("userId", user.getId());
        }
        return buildToken(extraClaims, userDetails, jwtExpirationToken);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("refreshToken", true);
        return buildToken(extraClaims, userDetails, jwtExpirationRefreshToken);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Optional<String> getTokenFromHttpServletRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Optional.empty();
        }
        return Optional.of(authHeader.substring(7));
    }
}
