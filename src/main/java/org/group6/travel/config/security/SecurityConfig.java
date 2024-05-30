package org.group6.travel.config.security;

import lombok.RequiredArgsConstructor;
import org.group6.travel.domain.token.provider.TokenProvider;
import org.group6.travel.domain.token.provider.TokenProviderNew;
import org.group6.travel.domain.token.service.JwtAuthenticationFilter;
import org.group6.travel.domain.token.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProviderNew tokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


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
                .anyRequest().authenticated()

                //.permitAll()
            );

        http.addFilterBefore(new JwtAuthenticationFilter(tokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}