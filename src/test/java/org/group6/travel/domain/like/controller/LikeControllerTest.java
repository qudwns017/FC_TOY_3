package org.group6.travel.domain.like.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.group6.travel.domain.itinerary.service.ItineraryService;
import org.group6.travel.domain.like.model.dto.LikeDto;
import org.group6.travel.domain.like.model.entity.LikeEntity;
import org.group6.travel.domain.like.repository.LikeRepository;
import org.group6.travel.domain.like.service.LikeService;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.group6.travel.domain.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private UserEntity userEntity;

    @MockBean
    private LikeService likeService;

    @BeforeEach
    public void beforeEach() {
        JacksonTester.initFields(this, objectMapper);
    }

    @BeforeEach
    void setUp() {
        Optional<UserEntity> existingUser = userRepository.findByEmail("testuser@test.com");
        if (existingUser.isEmpty()) {
            userEntity = createUser();
            userRepository.save(userEntity);
        } else {
            userEntity = existingUser.get();
        }
    }

    private UserEntity createUser() {
        return UserEntity.builder()
            .email("testuser@test.com")
            .encryptedPassword("password1@")
            .userName("test")
            .build();
    }

    @AfterEach
    public void end() throws Exception {
        userRepository.deleteById(userEntity.getUserId());
    }

    @Test
    @DisplayName("좋아요 조회 테스트")
    @WithMockUser
    void getlikes() throws Exception {
        List<?> expectedLikeList = Arrays.asList();
        // when & then
        mockMvc.perform(get("/api/user/like-list")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    }

    @DisplayName("좋아요 테스트")
    @Test
    @WithMockUser
    void addLikeTest() throws Exception {
        Long tripId = 1L;

        LikeDto likeDto = new LikeDto(1L, 1L);
        when(likeService.addLike(tripId, userEntity.getUserId())).thenReturn(likeDto);

        mockMvc.perform(post("/trip/{tripId}/like", tripId))
            .andExpect(status().isOk());
    }
}