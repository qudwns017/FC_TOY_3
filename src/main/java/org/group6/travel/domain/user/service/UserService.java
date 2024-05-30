package org.group6.travel.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.group6.travel.common.error.UserErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.token.model.response.TokenResponse;
import org.group6.travel.domain.token.service.TokenService;
import org.group6.travel.domain.user.model.converter.UserConverter;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.group6.travel.domain.user.model.request.UserLoginRequest;
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
    private final TokenService tokenService;

    public UserResponse getMyInfo(Long userId) {
        UserEntity user = getUserWithThrow(userId);
        return userConverter.toResponse(user);
    }

    public UserResponse register(UserRegisterRequest request) {
        UserEntity entity = userConverter.toEntity(request, passwordEncoder);
        UserEntity responseEntity = userRepository.save(entity);
        return userConverter.toResponse(responseEntity);
    }

    public TokenResponse login(
            UserLoginRequest request
    ) {
        UserEntity userEntity = getUserWithThrow(request.getEmail(), request.getPassword());
        return tokenService.issueToken(userEntity);
    }

    public UserEntity getUserWithThrow(String email, String password) {
        System.out.println(email + " " + password);

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, "Unregistered email"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApiException(UserErrorCode.INVALID_PASSWORD, "Invalid password");
        }

        return user;
    }

    public UserEntity getUserWithThrow(
            Long userId
    ) {
        return userRepository.findFirstByUserIdOrderByUserIdDesc(
                userId
        ).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }
}
