package org.group6.travel.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.group6.travel.domain.user.model.converter.UserConverter;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.group6.travel.domain.user.model.request.UserRegisterRequest;
import org.group6.travel.domain.user.model.response.UserResponse;
import org.group6.travel.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(UserRegisterRequest request) {
        UserEntity entity = userConverter.toEntity(request, passwordEncoder);
        UserEntity responseEntity = userRepository.save(entity);
        return userConverter.toResponse(responseEntity);
    }
}
