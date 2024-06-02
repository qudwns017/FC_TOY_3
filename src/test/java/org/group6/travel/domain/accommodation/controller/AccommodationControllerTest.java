package org.group6.travel.domain.accommodation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.aspectj.lang.annotation.After;
import org.group6.travel.domain.accommodation.model.dto.AccommodationDto;
import org.group6.travel.domain.accommodation.model.dto.AccommodationRequest;
import org.group6.travel.domain.accommodation.service.AccommodationService;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.group6.travel.domain.user.repository.UserRepository;
import org.group6.travel.testConfig.WithAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccommodationControllerTest {

    Long tripId = 1L;
    Long userId = 1234L;
    Long accommodationId = 1L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private AccommodationService accommodationService;

    private UserEntity userEntity;
    @Autowired
    private MockMvc mockMvc;

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
    public void end() {
        userRepository.deleteById(userEntity.getUserId());
    }

    @DisplayName("숙박 조회 테스트")
    @Test
    void getAccommodationList() throws Exception{
        //given
        List<AccommodationDto> accommodationDtoList = new ArrayList<>();
        AccommodationDto accommodationDto = AccommodationDto.builder()
            .tripId(tripId)
            .name("test name")
            .latitude(234.23)
            .longitude(23.23)
            .checkInDatetime(LocalDateTime.now())
            .checkOutDatetime(LocalDateTime.now().plusDays(3))
            .build();
        accommodationDtoList.add(accommodationDto);

        //when
        when(accommodationService.getAccommodationList(tripId)).thenReturn(accommodationDtoList);

        mvc.perform(MockMvcRequestBuilders.get("/api/trip/{tripId}/accommodation",tripId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tripId)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].trip_id").value(tripId))
            .andExpect(jsonPath("$.data[0].name").value("test name"))
            .andExpect(jsonPath("$.data[0].latitude").value(234.23))
            .andExpect(jsonPath("$.data[0].longitude").value(23.23))
            .andExpect(jsonPath("$.data[0].check_in_datetime").exists())
            .andExpect(jsonPath("$.data[0].check_out_datetime").exists());
    }

    @DisplayName("숙박 생성 테스트")
    @Test
    @WithMockUser
    void createAccommodation() throws Exception {
        AccommodationRequest accommodationRequest = AccommodationRequest.builder()
                .name("숙박")
                .checkInDatetime(LocalDateTime.now())
                .checkOutDatetime(LocalDateTime.now().plusDays(3))
                .address("주소")
                .build();

        AccommodationDto accommodationDto = AccommodationDto.builder()
            .tripId(tripId)
            .name("숙박")
            .latitude(234.23)
            .longitude(23.23)
            .checkInDatetime(LocalDateTime.now())
            .checkOutDatetime(LocalDateTime.now().plusDays(3))
            .build();

        given(accommodationService.createAccommodation(tripId, userId, accommodationRequest)).willReturn(accommodationDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/trip/{tripId}/accommodation", tripId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accommodationRequest)))
            .andDo(print())
            .andExpect(status().isOk());

    }

    @DisplayName("숙박 삭제 테스트")
    @Test
    @WithMockUser
    void deleteAccommodation() throws Exception {

        mvc.perform(delete("/api/trip/{tripId}/accommodation/{accommodationId}", tripId, accommodationId))
            .andDo(print())
            .andExpect(status().isOk());
    }
}