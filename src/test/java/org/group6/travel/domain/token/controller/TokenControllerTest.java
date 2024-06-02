package org.group6.travel.domain.token.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import org.group6.travel.domain.token.model.dto.RefreshTokenRequest;
import org.group6.travel.domain.token.model.entity.RefreshTokenEntity;
import org.group6.travel.domain.token.provider.TokenProvider;
import org.group6.travel.domain.token.repository.RefreshTokenRepository;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.group6.travel.domain.user.repository.UserRepository;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
class TokenControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    PasswordEncoder passwordEncoder;


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


    @DisplayName("createNewAccessToken : 새로운 액세스 토큰 발급")
    @Test
    void createNewAccessToken() throws Exception {
        // given
        final String url = "/api/token";

        String userName = "testUser";
        String userEmail = "test@naver.com";
        String userPassword = "test123";
        String encryptedUserPassword = passwordEncoder.encode(userPassword);

        UserEntity testUser = userRepository.save(UserEntity
            .builder()
            .email(userEmail)
            .userName(userName)
            .encryptedPassword(encryptedUserPassword).build()
        );

        var data = new HashMap<String, Object>();
        data.put("userId", testUser.getUserId());

        var refreshToken = tokenProvider.issueRefreshToken(data);

        var refreshTokenEntity = RefreshTokenEntity.builder()
            .userId(testUser.getUserId())
            .refreshToken(refreshToken.getToken())
            .expireAt(refreshToken.getExpiredAt())
            .build();

        refreshTokenRepository.save(refreshTokenEntity);

        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken(refreshToken.getToken());

        final String requestBody = objectMapper.writeValueAsString(request);


        // when

        Thread.sleep(500);

        ResultActions resultActions = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody));

        // then
        RefreshTokenEntity newRefToken = refreshTokenRepository.findByUserId(testUser.getUserId()).get();

        resultActions
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.refreshToken").value(newRefToken.getRefreshToken()));

        resultActions
            .andDo(print());

    }
}