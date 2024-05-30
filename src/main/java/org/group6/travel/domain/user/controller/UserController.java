package org.group6.travel.domain.user.controller;

import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.group6.travel.common.api.ResponseApi;
import org.group6.travel.domain.token.model.response.TokenResponse;
import org.group6.travel.domain.user.model.request.UserLoginRequest;
import org.group6.travel.domain.user.model.request.UserRegisterRequest;
import org.group6.travel.domain.user.model.response.UserResponse;
import org.group6.travel.domain.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

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
    public ResponseApi<?> login(
            @Valid
            @RequestBody UserLoginRequest request
    ) {
        String response = service.login(request);
        return ResponseApi.OK(response);
    }
}
