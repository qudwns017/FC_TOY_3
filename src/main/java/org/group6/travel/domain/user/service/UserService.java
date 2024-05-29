package org.group6.travel.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.group6.travel.domain.token.model.response.TokenResponse;
import org.group6.travel.domain.user.model.converter.UserConverter;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.group6.travel.domain.user.model.request.UserLoginRequest;
import org.group6.travel.domain.user.model.request.UserRegisterRequest;
import org.group6.travel.domain.user.model.response.UserResponse;
import org.group6.travel.domain.user.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserResponse register(UserRegisterRequest request) {
        UserEntity entity = userConverter.toEntity(request, passwordEncoder);
        UserEntity responseEntity = userRepository.save(entity);
        return userConverter.toResponse(responseEntity);
    }

//    public TokenResponse login(UserLoginRequest request) {
//        UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();
//        Authentication authentication = authenticationManagerBuilder
//                .getObject()
//                .authenticate(authenticationToken);
//
//    }
}
