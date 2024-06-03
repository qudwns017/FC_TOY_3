package org.group6.travel.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.group6.travel.config.security.entrypoint.CustomAuthenticationEntryPoint;
import org.group6.travel.domain.token.filter.TokenAuthenticationFilter;
import org.group6.travel.domain.token.provider.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-resources/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/trip",
                                "/api/trip/{tripId}",
                                "/api/trip/search",
                                "/api/trip/{tripId}/reply",
                                "/api/trip/{tripId}/accommodation",
                                "/api/trip/{tripId}/itinerary"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/api/user/register",
                                "/api/user/login",
                                "/api/token"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling((exceptionConfig) -> exceptionConfig.authenticationEntryPoint(
                        new CustomAuthenticationEntryPoint(new ObjectMapper())))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new TokenAuthenticationFilter(tokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}