package org.group6.travel.domain.user.model.converter;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.group6.travel.common.status.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.group6.travel.domain.user.model.request.UserRegisterRequest;
import org.group6.travel.domain.user.model.response.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
// TODO UserDto Static Method로 이동시키기
public class UserConverter {
    public UserEntity toEntity(UserRegisterRequest request, PasswordEncoder passwordEncoder) {
        return Optional.ofNullable(request)
                .map(it -> UserEntity.builder()
                        .email(request.getEmail())
                        .encryptedPassword(passwordEncoder.encode(request.getPassword()))
                        .userName(request.getName())
                        .build())
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest is null"));
    }

    public UserResponse toResponse(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(it -> UserResponse.builder()
                        .id(userEntity.getUserId())
                        .name(userEntity.getUserName())
                        .email(userEntity.getEmail())
                        .build())
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserEntity is null"));
    }
}
