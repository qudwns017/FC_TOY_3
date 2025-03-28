package org.group6.travel.domain.user.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.token.model.dto.RefreshTokenRequest;
import org.group6.travel.domain.token.model.dto.TokenResponse;
import org.group6.travel.domain.token.service.TokenService;
import org.group6.travel.domain.user.model.converter.UserConverter;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.group6.travel.domain.user.model.request.UserLoginRequest;
import org.group6.travel.domain.user.model.request.UserRegisterRequest;
import org.group6.travel.domain.user.model.response.UserResponse;
import org.group6.travel.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UserResponse getMyInfo() {
        var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
        var userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
        UserEntity user = getUserWithThrow(Long.parseLong(userId.toString()));
        return userConverter.toResponse(user);
    }

    public UserResponse register(UserRegisterRequest request) {
        UserEntity entity = userConverter.toEntity(request, passwordEncoder);
        if (userRepository.existsByEmail(entity.getEmail())) {
            throw new ApiException(ErrorCode.USER_NOT_MATCH, "Already exists email");
        }
        UserEntity responseEntity = userRepository.save(entity);
        return userConverter.toResponse(responseEntity);
    }

    // JWT refreshToken 관리를 redis가 아닌 DBMS에서 하고 있기 때문에 readonly 불가
    @Transactional
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
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND, "Unregistered email"));

        if (!passwordEncoder.matches(password, user.getEncryptedPassword())) {
            throw new ApiException(ErrorCode.INVALID_PASSWORD, "Invalid password");
        }

        return user;
    }

    @Transactional(readOnly = true)
    public UserEntity getUserWithThrow(
            Long userId
    ) {
        return userRepository.findFirstByUserIdOrderByUserIdDesc(
                userId
        ).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public void logout(RefreshTokenRequest refreshTokenRequest) {
        tokenService.deleteRefreshToken(refreshTokenRequest);
    }
}
