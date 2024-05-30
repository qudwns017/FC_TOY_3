package org.group6.travel.domain.user.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.group6.travel.common.error.ErrorCode;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    public UserResponse register(UserRegisterRequest request) {
        UserEntity entity = userConverter.toEntity(request, passwordEncoder);
        UserEntity responseEntity = userRepository.save(entity);
        return userConverter.toResponse(responseEntity);
    }

    @Transactional(readOnly = true)
    public TokenResponse login(
            UserLoginRequest request
    ) {
        UserEntity userEntity = getUserWithThrow(request.getEmail(), request.getPassword());
        return tokenService.issueToken(userEntity);
    }

    @Transactional(readOnly = true)
    public UserEntity getUserWithThrow(String email, String password) {
        System.out.println(email + " " + password);

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, "Unregistered email"));

        Optional.of(password)
                .filter(pw -> passwordEncoder.matches(pw, user.getEncryptedPassword()))
                .orElseThrow(() -> new ApiException(UserErrorCode.INVALID_PASSWORD, "Invalid password"));

        return user;
    }
}
