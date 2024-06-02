package org.group6.travel.domain.trip.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.Optional;
import org.group6.travel.domain.trip.model.dto.TripDto;
import org.group6.travel.domain.trip.model.dto.TripRequest;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.model.enums.DomesticType;
import org.group6.travel.domain.trip.service.TripService;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.group6.travel.domain.user.repository.UserRepository;
import org.hibernate.annotations.DialectOverride;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TripControllerTest {

    Long userId = 1123124L;
    Long tripId = 1L;

    @LocalServerPort
    private int port;
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private TripService tripService;

    @Autowired
    private UserRepository userRepository;

    @MockBean
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
    public void end() throws Exception {
        userRepository.deleteById(userEntity.getUserId());
    }

    @Test
    @DisplayName("여행 리스트 조회 테스트")
    void getTripsTest() throws Exception {
        //given
        List<TripDto> tripDtoList = new ArrayList<>();
        TripDto tripDto = new TripDto((long) tripId, userId, "tripname", LocalDate.now(), LocalDate.now().plusDays(10), DomesticType.OVERSEAS, 1, "comment");
        tripDtoList.add(tripDto);

        //when
        when(tripService.getTrips()).thenReturn(tripDtoList);

        mvc.perform(get("/api/trip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tripDtoList))) // tripDtoList를 전달해야 함
            .andDo(print())
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("여행 검색 테스트")
    void getTripsByKeywordTest() throws Exception {
        String keyword = "tripname";
        List<TripDto> tripDtoList = new ArrayList<>();
        TripDto tripDto = new TripDto((long) tripId, userId, "tripname", LocalDate.now(), LocalDate.now().plusDays(10), DomesticType.OVERSEAS, 1, "comment");
        tripDtoList.add(tripDto);

        when(tripService.getTripsByKeyword(keyword)).thenReturn(tripDtoList);

        mvc.perform(get("/api/trip/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tripDtoList)))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    void getTripIdTest() throws Exception {

        TripEntity tripEntity = TripEntity.builder()
            .tripId(tripId)
            .userId(userId)
            .tripName("tripname")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(10))
            .domestic(DomesticType.OVERSEAS)
            .tripComment("df")
            .likeCount(1)
            .build();

        when(tripService.getTripById(tripId)).thenReturn(tripEntity);

        mvc.perform(get("/api/trip/{tripId}",tripId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tripId)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.tripId").value(tripId))
            .andExpect(jsonPath("$.data.userId").value(userId))
            .andExpect(jsonPath("$.data.tripName").value("tripname"))
            .andExpect(jsonPath("$.data.startDate").value(LocalDate.now().toString()))
            .andExpect(jsonPath("$.data.endDate").value(LocalDate.now().plusDays(10).toString()))
            .andExpect(jsonPath("$.data.likeCount").value(1))
            .andExpect(jsonPath("$.data.domestic").value(DomesticType.OVERSEAS.toString()))
            .andExpect(jsonPath("$.data.tripComment").value("df"));
    }

    @DisplayName("좋아요 여행 리스트 테스트")
    @Test
    @WithMockUser
    void getTripsByUserLikeTest() throws Exception {
        List<TripEntity> trips = Collections.emptyList();

        when(tripService.getTripsByUserLike(userEntity.getUserId())).thenReturn(trips);

        mockMvc.perform(get("/user/like-list"))
            .andExpect(status().isOk());
    }

    @DisplayName("여행 생성 테스트")
    @Test
    @WithMockUser
    void createTripTest() throws Exception {
        TripRequest tripRequest = TripRequest.builder()
            .tripName("tripname")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(10))
            .domestic(DomesticType.OVERSEAS)
            .tripComment("df")
            .build();

        TripDto tripDto = TripDto.builder()
            .userId(userEntity.getUserId())
            .tripName(tripRequest.getTripName())
            .startDate(tripRequest.getStartDate())
            .endDate(tripRequest.getEndDate())
            .domestic(tripRequest.getDomestic())
            .likeCount(0)
            .tripComment(tripRequest.getTripComment())
            .build();

        when(tripService.createTrip(tripRequest, userEntity.getUserId())).thenReturn(tripDto);

        mockMvc.perform(post("/")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(tripRequest)))
            .andExpect(status().isOk());
    }

    @DisplayName("여행 수정 테스트")
    @Test
    @WithMockUser
    void updateTripTest() throws Exception {
        Long tripId = 1L;

        TripRequest tripRequest = TripRequest.builder()
            .tripName("tripname")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(10))
            .domestic(DomesticType.DOMESTIC)
            .tripComment("df")
            .build();

        TripEntity tripEntity = TripEntity.builder()
            .tripId(tripId)
            .userId(userEntity.getUserId())
            .tripName("tripname")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(10))
            .domestic(DomesticType.DOMESTIC)
            .tripComment("df")
            .likeCount(1)
            .build();

        when(tripService.updateTrip(tripId, tripRequest, userEntity.getUserId())).thenReturn(tripEntity);

        mockMvc.perform(put("/api/trip/{tripId}",tripId)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(tripRequest)))
            .andExpect(status().isOk());
    }

    @DisplayName("여행 삭제 테스트")
    @Test
    @WithMockUser
    void deleteTripTest() throws Exception {
        Long tripId = 1L;

        doNothing().when(tripService).deleteTrip(tripId, userEntity.getUserId());

        mockMvc.perform(delete("/api/trip/{tripId}",tripId))
            .andExpect(status().isOk());
    }
}