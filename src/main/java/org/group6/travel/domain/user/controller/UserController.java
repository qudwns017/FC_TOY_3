package org.group6.travel.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.group6.travel.common.api.Api;
import org.group6.travel.domain.token.model.response.TokenResponse;
import org.group6.travel.domain.user.model.request.UserLoginRequest;
import org.group6.travel.domain.user.model.request.UserRegisterRequest;
import org.group6.travel.domain.user.model.response.UserResponse;
import org.group6.travel.domain.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;

    @PostMapping("/register")
    public Api<UserResponse> register(
            @Valid
            @RequestBody UserRegisterRequest request
    ) {
        UserResponse response = service.register(request);
        return Api.OK(response);
    }

    @PostMapping("/login")
    public Api<TokenResponse> login(
            @Valid
            @RequestBody UserLoginRequest request
    ) {
        System.out.println(request.toString());
        TokenResponse response = service.login(request);
        return Api.OK(response);
    }
}
