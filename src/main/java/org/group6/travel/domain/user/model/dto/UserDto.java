package org.group6.travel.domain.user.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group6.travel.common.status.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.group6.travel.domain.user.model.response.UserResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDto {
    private Long userid;
    private String email;
    private String password;
    private String userName;

    public static UserResponse toResponse(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(it -> UserResponse.builder()
                        .id(userEntity.getUserId())
                        .email(userEntity.getEmail())
                        .name(userEntity.getUserName())
                        .build())
                .orElseThrow(() ->
                        new ApiException(ErrorCode.NULL_POINT, "UserEntity Null"));
    }
}
