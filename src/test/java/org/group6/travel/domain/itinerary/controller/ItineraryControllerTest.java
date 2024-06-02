package org.group6.travel.domain.itinerary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.group6.travel.domain.itinerary.model.dto.ItineraryDto;
import org.group6.travel.domain.itinerary.model.dto.ItineraryRequest;
import org.group6.travel.domain.itinerary.model.enums.ItineraryType;
import org.group6.travel.domain.itinerary.service.ItineraryService;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.group6.travel.domain.user.repository.UserRepository;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // 예시로 get 메서드 import
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItineraryControllerTest {

    Long tripId = 1L;
    Long userId = 1234L;
    Long itineraryId = 1234L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private ItineraryService itineraryService;

    private UserEntity userEntity;

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

    @DisplayName("여정 조회 테스트")
    @Test //성공
    void getItineraries() throws Exception{
        //Given
        List<ItineraryDto> itineraryDtoList = new ArrayList<>();
        ItineraryDto itineraryDto = new ItineraryDto();
        itineraryDto.setItineraryId(1L);
        itineraryDto.setTripId(tripId);
        itineraryDto.setItineraryName("Test Itinerary");
        itineraryDto.setType(ItineraryType.MOVE);
        itineraryDto.setStartDatetime(LocalDateTime.now().plusHours(1));
        itineraryDto.setEndDatetime(LocalDateTime.now().plusHours(2));
        itineraryDto.setItineraryComment("This is a test itinerary.");
        itineraryDto.setTransportation("Car");
        itineraryDto.setDeparturePlace("Departure Place");
        itineraryDto.setArrivalPlace("Arrival Place");
        itineraryDto.setDepartureLatitude(37.5123);
        itineraryDto.setDepartureLongitude(127.0456);
        itineraryDto.setArrivalLatitude(37.5789);
        itineraryDto.setArrivalLongitude(127.1011);
        itineraryDto.setPlace("Test Place");
        itineraryDto.setLatitude(37.6123);
        itineraryDto.setLongitude(127.2345);
        itineraryDtoList.add(itineraryDto);

        //when
        when(itineraryService.getItinerary(tripId)).thenReturn(itineraryDtoList);

        mvc.perform(MockMvcRequestBuilders.get("/api/trip/{tripId}/itinerary", tripId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tripId)))
            .andDo(print())
            .andExpectAll(status().isOk());
    }

    @DisplayName("여정 생성 테스트")
    @Test
    @WithMockUser
    void createItinerary() throws Exception{
        ItineraryRequest itineraryRequest = new ItineraryRequest();
        itineraryRequest.setItineraryName("Test Itinerary");
        itineraryRequest.setType(ItineraryType.MOVE);
        itineraryRequest.setStartDatetime(LocalDateTime.now().plusHours(1));
        itineraryRequest.setEndDatetime(LocalDateTime.now().plusHours(2));
        itineraryRequest.setItineraryComment("This is a test itinerary.");
        itineraryRequest.setTransportation("Car");
        itineraryRequest.setDeparturePlace("Departure Place");
        itineraryRequest.setArrivalPlace("Arrival Place");
        itineraryRequest.setPlace("Test Place");

        given(itineraryService.createItinerary(itineraryRequest, tripId, userId)).willReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/api/trip/{tripId}/itinerary/{itineraryId}", tripId, itineraryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itineraryRequest)))
            .andDo(print())
            .andExpectAll(status().isOk());
    }

    @DisplayName("여정 업데이트 테스트")
    @Test
    @WithMockUser
    void updateItineraryTest() throws Exception {
        Long tripId = 1L;
        Long itineraryId = 1L;
        ItineraryRequest itineraryRequest = new ItineraryRequest();
        itineraryRequest.setItineraryName("Test Itinerary");
        itineraryRequest.setType(ItineraryType.MOVE);
        itineraryRequest.setStartDatetime(LocalDateTime.now().plusHours(1));
        itineraryRequest.setEndDatetime(LocalDateTime.now().plusHours(2));
        itineraryRequest.setItineraryComment("This is a test itinerary.");
        itineraryRequest.setTransportation("Car");
        itineraryRequest.setDeparturePlace("Departure Place");
        itineraryRequest.setArrivalPlace("Arrival Place");
        itineraryRequest.setPlace("Test Place");
        ItineraryDto itineraryDto = new ItineraryDto();

        when(itineraryService.updateItinerary(tripId, itineraryId, userId, itineraryRequest)).thenReturn(itineraryDto);

        mvc.perform(MockMvcRequestBuilders.put("/itinerary/{itineraryId}", itineraryId)
                .param("tripId", String.valueOf(tripId))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(itineraryRequest)))
            .andExpect(status().isOk());
    }

    @DisplayName("여정 업데이트 테스트")
    @Test
    @WithMockUser
    void deleteItineraryTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete("/api/trip/{tripId}/itinerary/{itineraryId", tripId, itineraryId)
                .param("tripId", String.valueOf(tripId)))
            .andExpect(status().isOk());
    }
}