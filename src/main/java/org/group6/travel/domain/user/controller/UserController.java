package org.group6.travel.domain.user.controller;

import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.group6.travel.common.api.ResponseApi;
import org.group6.travel.domain.token.model.dto.TokenResponse;
import org.group6.travel.domain.user.model.request.UserLoginRequest;
import org.group6.travel.domain.user.model.request.UserRegisterRequest;
import org.group6.travel.domain.user.model.response.UserResponse;
import org.group6.travel.domain.user.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.User;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;

    @GetMapping
    public ResponseApi<UserResponse> getMyInfo() {
        UserResponse response = service.getMyInfo();
        return ResponseApi.OK(response);
    }

    @PostMapping("/register")
    public ResponseApi<UserResponse> register(
            @Valid
            @RequestBody UserRegisterRequest request
    ) {
        UserResponse response = service.register(request);
        return ResponseApi.OK(response);
    }

    @PostMapping("/login")
    public ResponseApi<TokenResponse> login(
            @Valid
            @RequestBody UserLoginRequest request
    ) {
        TokenResponse response = service.login(request);
        return ResponseApi.OK(response);
    }

    @PostMapping("/logout")
    public ResponseApi<?> logout(
            String refreshToken
    ) {
        service.logout(refreshToken);
        return ResponseApi.OK("logout successful");
    }
}
