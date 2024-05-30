package org.group6.travel.domain.token.provider;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.token.model.dto.TokenDto;
import org.group6.travel.domain.token.model.entity.UserDetailsEntity;
import org.group6.travel.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

//    @Value("${jwt.expiration_time}")
//    private static int EXPIRATION_TIME;


    public TokenDto issueAccessToken(Map<String, Object> data) {
        var expiredLocalDateTime = LocalDateTime.now().plusHours(4);

        var expiredAt = Date.from(
                expiredLocalDateTime.atZone(
                        ZoneId.systemDefault()
                ).toInstant()
        );

        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

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
        var expiredLocalDateTime = LocalDateTime.now().plusHours(12);

        var expiredAt = Date.from(
                expiredLocalDateTime.atZone(
                        ZoneId.systemDefault()
                ).toInstant()
        );

        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

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
        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        try{
            var result = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);

            return result.getPayload();

        } catch (Exception e){

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

    public boolean validateToken(String token) {
        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        try{
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                ;

            return true;

        } catch (Exception e){
            if(e instanceof SignatureException){
                throw new ApiException(ErrorCode.INVALID_TOKEN, e);
            }
            else if(e instanceof ExpiredJwtException){
                throw new ApiException(ErrorCode.EXPIRED_TOKEN, e);
            }
            else {
                throw new ApiException(ErrorCode.TOKEN_EXCEPTION, e);
            }
        }
    }

    public Authentication getAuthentication(String token) {
        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        var claims = Jwts.parser()
            .verifyWith((SecretKey) key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            ;

        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(
            new User(claims.get("userId", Long.class).toString(), "", authorities), token, authorities);

    }
}
