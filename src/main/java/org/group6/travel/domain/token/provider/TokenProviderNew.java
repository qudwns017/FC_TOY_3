package org.group6.travel.domain.token.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.token.service.UserDetailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class TokenProviderNew {
    private final UserDetailService userDetailService;

    @Value("${jwt.secret}")
    private String secretKeyStr;
    private Key secretKey;
    @Value("${jwt.expiration_time}")
    private long tokenValidTime;

    @PostConstruct
    protected void init(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKeyStr);
        secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String user){
        Date date = new Date();
        String token = Jwts.builder()
                .signWith(secretKey)
                .expiration(new Date(date.getTime() + tokenValidTime))
                .issuedAt(new Date())
                .claim("email", user)
                .compact();
        log.info("토큰 : " + token);
        return token;
    }

    public Authentication getAuthentication(String token){
        log.info(token);
        UserDetails userDetails = userDetailService.loadUserByUsername(this.getUserEmail(token));

        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getUserEmail(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("email",String.class);
    }

    public String resolveToken(HttpServletRequest request){
        if(request.getHeader("authorization") != null)
            return request.getHeader("authorization").substring(7);
        return null;
    }

    public boolean validateToken(String token){
        try{
            var claims = Jwts.parser()
                    .verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            System.out.println(claims.getExpiration().after(new Date()));
            log.info(new Date().toString());
            return claims.getExpiration().after(new Date());
        }catch (ExpiredJwtException ex){
            throw new ApiException(ErrorCode.EXPIRED_TOKEN);
        }catch (JwtException ex){
            throw  new ApiException(ErrorCode.INVALID_TOKEN);
        }
    }
}
