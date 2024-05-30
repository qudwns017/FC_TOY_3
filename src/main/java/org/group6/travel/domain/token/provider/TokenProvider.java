package org.group6.travel.domain.token.provider;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.token.model.dto.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenProvider {

    private final Key key;

//    @Value("${jwt.expiration_time}")
//    private static int EXPIRATION_TIME;

    public TokenProvider(
            @Value("${jwt.secret}")
            String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto issueAccessToken(Map<String, Object> data) {
        var expiredLocalDateTime = LocalDateTime.now().plusHours(4);

        var expiredAt = Date.from(
                expiredLocalDateTime.atZone(
                        ZoneId.systemDefault()
                ).toInstant()
        );

        var jwtToken = Jwts.builder()
                .signWith(key)
                .claims(data)
                .expiration(expiredAt)
                .compact();

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build();
    }

    public TokenDto issueRefreshToken(Map<String, Object> data) {
        var expiredLocalDateTime = LocalDateTime.now().plusHours(4);

        var expiredAt = Date.from(
                expiredLocalDateTime.atZone(
                        ZoneId.systemDefault()
                ).toInstant()
        );

        var jwtToken = Jwts.builder()
                .signWith(key)
                .claims(data)
                .expiration(expiredAt)
                .compact();

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build();
    }

    public Map<String, Object> validationTokenWithThrow(String token) {
        var parser = Jwts.parser()
                .setSigningKey(key)
                .build();

        try{
            var result = parser.parseClaimsJws(token);
            return new HashMap<String, Object>(result.getBody());

        }catch (Exception e){

            if(e instanceof SignatureException){
                throw new ApiException(ErrorCode.INVALID_TOKEN, e);
            }
            else if(e instanceof ExpiredJwtException){
                throw new ApiException(ErrorCode.EXPIRED_TOKEN, e);
            }
            else{
                throw new ApiException(ErrorCode.TOKEN_EXCEPTION, e);
            }
        }
    }
}
