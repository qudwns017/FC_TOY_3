package org.group6.travel.domain.token.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.token.provider.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public final TokenProvider tokenProvider;

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//
//        String[] excludePostPath = {
//                "/api/user/register",
//                "/api/user/login"};
//
//        String[] excludeGetPath = {
//                "/api/trip",
//                "/api/trip/{tripId}",
//                "/api/trip/search",
//                "/api/trip/{tripId}/reply",
//                "/api/trip/{tripId}/accommodation",
//                "/api/trip/{tripId}/itinerary"
//        };
//        String path = request.getRequestURI();
//        String method = request.getMethod();
//        if (method.equals("GET")) {
//            return Arrays.stream(excludeGetPath).anyMatch(path::startsWith);
//        }
//        if (method.equals("POST")) {
//            return Arrays.stream(excludePostPath).anyMatch(path::startsWith);
//        }
//        return false;
//    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String token = resolveToken(request);

            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("TokenAuthenticationFilter ERROR");
            throw new ApiException(ErrorCode.INVALID_TOKEN, "Authorization Error");
        }

    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
