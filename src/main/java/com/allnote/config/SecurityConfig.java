package com.allnote.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final String[] WHITE_LIST_URL = {
            "/api/notes/**",
            "/api/auth/**",
            "/h2-console/**"
    };

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //todo 4. sending mails and reset/forget password
        //todo 5. add tags to notes
        //todo 6. add rabbitmq and make send mailing with using it (in the same application)
        //todo . tests (unit i integration)
        return http
                .csrf(csrf -> {
                    csrf.disable();
                })
                .cors(cors -> {//todo when implementing frontend
                    cors.disable();
                })
                .authorizeHttpRequests(auth -> {
                    Arrays.stream(WHITE_LIST_URL).forEach(url ->
                            auth.requestMatchers(AntPathRequestMatcher.antMatcher(url)).permitAll());
//                    auth.requestMatchers(AntPathRequestMatcher.antMatcher("/api/users/**")).hasAnyRole(Role.ADMIN.name())
//                    auth.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/users/**")).hasAnyAuthority(Permission.ADMIN_READ.getPermission())
                    auth.anyRequest().authenticated();
                })
                .headers(headers -> {//todo delete after changing db for another than h2
                    headers.frameOptions().disable();
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
