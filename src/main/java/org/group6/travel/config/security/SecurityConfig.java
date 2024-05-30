package org.group6.travel.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

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
                        /*.requestMatchers(HttpMethod.GET,
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
                        .permitAll()*/
                        .anyRequest()
                        /*.authenticated()*/
                        .permitAll()
                );

        return http.build();
    }
}
