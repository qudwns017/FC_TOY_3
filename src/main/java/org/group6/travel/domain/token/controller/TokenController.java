package org.group6.travel.domain.token.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.group6.travel.domain.token.model.dto.RefreshTokenRequest;
import org.group6.travel.domain.token.model.dto.TokenResponse;
import org.group6.travel.domain.token.service.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/api/token")
    public TokenResponse createNewAccessToken(
        @RequestBody RefreshTokenRequest refreshTokenRequest
    ) {
        log.info("testController : {}", refreshTokenRequest.getRefreshToken());
        return tokenService.issueNewAccessToken(refreshTokenRequest);
    }

}
