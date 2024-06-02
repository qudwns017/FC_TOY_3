package org.group6.travel.domain.user.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;
import org.group6.travel.domain.token.model.dto.TokenResponse;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.group6.travel.domain.user.model.request.UserLoginRequest;
import org.group6.travel.domain.user.repository.UserRepository;
import org.group6.travel.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserControllerTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    //@Spy
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void init() {
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Test
    void register() {

    }

    @Test
    void login() {
        // given
        String userName = "testUser";
        String userEmail = "test@naver.com";
        String userPassword = "test123";
        String encryptedUserPassword = passwordEncoder.encode(userPassword);

        UserEntity testUser = UserEntity
            .builder()
            .userId(1L)
            .email(userEmail)
            .userName(userName)
            .encryptedPassword(encryptedUserPassword)
            .build()
            ;

        when(userRepository.save(any(UserEntity.class))).thenReturn(testUser);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(testUser));

        UserEntity savedUserd = userRepository.save(testUser);

        System.out.println("***");
        System.out.println(savedUserd);
        System.out.println("***");


        // when

        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setEmail(userEmail);
        loginRequest.setPassword(userPassword);

        TokenResponse result = userService.login(loginRequest);

         System.out.println(result);

        // then
        assertNotNull(result);




    }
}