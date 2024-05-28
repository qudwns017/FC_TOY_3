package org.group6.travel.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(HttpMethod.GET,
                                "/api/trip",
                                "/api/trip/{tripId}",
                                "/api/trip/search",
                                "/api/trip/{tripId}/reply",
                                "/api/trip/{tripId}/accommodation",
                                "/api/trip/{tripId}/itinerary")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/api/user/register",
                                "/api/user/login")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                );

        return http.build();
    }
}
