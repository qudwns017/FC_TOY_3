package org.group6.travel.domain.user.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.group6.travel.domain.token.model.dto.TokenResponse;
import org.group6.travel.domain.token.repository.RefreshTokenRepository;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.group6.travel.domain.user.model.request.UserLoginRequest;
import org.group6.travel.domain.user.model.request.UserRegisterRequest;
import org.group6.travel.domain.user.repository.UserRepository;
import org.group6.travel.domain.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .build();
        userRepository.deleteAll();
        refreshTokenRepository.deleteAll();
    }

    @AfterEach
    void clearRepository() {
        userRepository.deleteAll();
        refreshTokenRepository.deleteAll();
    }


    @DisplayName("register : 사용자 가입 테스트")
    @Test
    void register() throws Exception {
        // given
        final String url = "/api/user/register";

        String userName = "testUser";
        String userEmail = "test@naver.com";
        String userPassword = "test123";

        UserRegisterRequest request = new UserRegisterRequest();
        request.setName(userName);
        request.setEmail(userEmail);
        request.setPassword(userPassword);

        final String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody));

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.data.id").value(notNullValue()))
            .andExpect(jsonPath("$.data.email").value(userEmail))
            .andExpect(jsonPath("$.data.name").value(userName));


    }

    @DisplayName("login : 로그인하여 Token이 발급되는지 확인")
    @Test
    void login() throws Exception {
        // given
        final String url = "/api/user/login";

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

        userRepository.save(testUser);

        UserLoginRequest request = new UserLoginRequest();
        request.setEmail(userEmail);
        request.setPassword(userPassword);

        final String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody));

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.data.accessToken").value(notNullValue()))
            .andExpect(jsonPath("$.data.refreshToken").value(notNullValue()));
    }
}