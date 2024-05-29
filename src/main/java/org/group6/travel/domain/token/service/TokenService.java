package org.group6.travel.domain.token.service;

import java.util.HashMap;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.token.model.dto.TokenDto;
import org.group6.travel.domain.token.model.response.TokenResponse;
import org.group6.travel.domain.token.provider.TokenProvider;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;

    public TokenResponse issueToken(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(UserEntity::getUserId)
                .map(userId -> {
                    TokenDto accessToken = issueAccessToken(userId);
                    TokenDto refreshToken = issueRefreshToken(userId);
                    return TokenDto.toResponse(accessToken, refreshToken);
                }).orElseThrow(
                        () -> new ApiException(ErrorCode.NULL_POINT)
                );
    }

    public TokenDto issueAccessToken(Long userId) {
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenProvider.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(Long userId) {
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenProvider.issueRefreshToken(data);
    }
}
