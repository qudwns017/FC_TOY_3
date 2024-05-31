package org.group6.travel.domain.like.controller;

import org.group6.travel.domain.like.model.entity.LikeEntity;
import org.group6.travel.domain.like.repository.LikeRepository;
import org.group6.travel.domain.like.service.LikeService;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LikeService likeService;

//    @Test
//    @DisplayName("좋아요 조회 테스트")
//    void getlikes() throws Exception{
//        List<LikeEntity> likeList = new ArrayList<>();
//        LikeEntity like1 = new LikeEntity(1L, 1L);
//        LikeEntity like2 = new LikeEntity(2L, 2L);
//        likeList.add(like1);
//        likeList.add(like2);
//
//        when(likeService.getlikes()).thenReturn(likeList);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/user/like-list"))
//            .andExpect(MockMvcResultMatchers.status().isOk())
//            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
//            .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(2))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].userId").value(1))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].tripId").value(1))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].userId").value(2))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].tripId").value(2));
//    }
}