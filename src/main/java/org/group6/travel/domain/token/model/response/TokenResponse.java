package org.group6.travel.domain.token.model.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponse {

    private String accessToken;
    private LocalDateTime accessTokenExpiredAt;

    private String refreshToken;
    private LocalDateTime refreshTokenExpiredAt;
}
