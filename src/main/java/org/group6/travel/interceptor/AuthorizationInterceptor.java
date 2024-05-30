package org.group6.travel.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.token.service.TokenService;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    /*private final TokenService tokenService;

    private static final List<String> EXCLUDED_URIS = List.of(
            "/api/trip",
            "/api/trip/{tripId}",
            "/api/trip/search",
            "/api/trip/{tripId}/reply",
            "/api/trip/{tripId}/accommodation",
            "/api/trip/{tripId}/itinerary"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("Authorization Interceptor url : {}", request.getRequestURI());

        // WEB ,chrome 의 경우 GET, POST OPTIONS = pass
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        // js. html. png resource 를 요청하는 경우 = pass
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        if (HttpMethod.GET.matches(request.getMethod()) && isExcludedUri(request.getRequestURI())) {
            return true;
        }

        var accessToken = request.getHeader("authorization-token");
        if (accessToken == null) {
            throw new ApiException(ErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
        }

        var userId = tokenService.validationAccessToken(accessToken);


        if (userId != null) {
            var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
            requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);
            return true;
        }

        throw new ApiException(ErrorCode.BAD_REQUEST, "Failed to authorization");
    }

    private boolean isExcludedUri(String requestUri) {
        return EXCLUDED_URIS.stream().anyMatch(uri -> uri.equalsIgnoreCase(requestUri));
    }*/
}
