package org.group6.travel.domain.token.service;

import java.util.HashMap;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.group6.travel.common.status.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.token.model.dto.TokenDto;
import org.group6.travel.domain.token.model.entity.RefreshTokenEntity;
import org.group6.travel.domain.token.model.dto.RefreshTokenRequest;
import org.group6.travel.domain.token.model.dto.TokenResponse;
import org.group6.travel.domain.token.provider.TokenProvider;
import org.group6.travel.domain.token.repository.RefreshTokenRepository;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

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

    @Transactional
    public TokenDto issueRefreshToken(Long userId) {
        var data = new HashMap<String, Object>();
        data.put("userId", userId);

        var refreshToken = tokenProvider.issueRefreshToken(data);

        var newRefreshTokenEntity = RefreshTokenEntity.builder()
            .userId(userId)
            .refreshToken(refreshToken.getToken())
            .expireAt(refreshToken.getExpiredAt())
            .build();

        refreshTokenRepository.save(newRefreshTokenEntity);

        return refreshToken;
    }


    @Transactional
    public TokenResponse issueNewAccessToken(RefreshTokenRequest refreshTokenRequest) {

        String refreshToken = refreshTokenRequest.getRefreshToken();

        log.info("test : {}", refreshToken);

        tokenProvider.validateToken(refreshToken);



        var refreshTokenEntity = refreshTokenRepository.findByRefreshToken(refreshToken)
            .orElseThrow(() -> new ApiException(ErrorCode.INVALID_TOKEN));

        Long userId = refreshTokenEntity.getUserId();

        TokenDto newAccessToken = issueAccessToken(userId);
        TokenDto newRefreshToken = issueRefreshToken(userId);

        refreshTokenRepository.delete(refreshTokenEntity);

        return TokenDto.toResponse(newAccessToken, newRefreshToken);
    }

    @Transactional
    public void deleteRefreshToken(RefreshTokenRequest refreshTokenRequest) {

        String refreshToken = refreshTokenRequest.getRefreshToken();
        var entity = refreshTokenRepository.findByRefreshToken(refreshToken);

        if(entity.isEmpty()) return;

        refreshTokenRepository.delete(entity.get());
    }
}
