package org.group6.travel.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.user.model.dto.UserDto;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.group6.travel.domain.user.model.request.UserRegisterRequest;
import org.group6.travel.domain.user.model.response.UserResponse;
import org.group6.travel.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse register(UserRegisterRequest request) {
        var entity = UserEntity.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .userName(request.getName())
                .build();

        if (entity == null) {
            throw new ApiException(ErrorCode.NULL_POINT, "User Entity Null");
        }
        UserEntity responseEntity = userRepository.save(entity);
        return UserDto.toResponse(responseEntity);
    }
}
