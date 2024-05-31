package org.group6.travel.domain.itinerary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.group6.travel.domain.itinerary.model.dto.ItineraryDto;
import org.group6.travel.domain.itinerary.model.dto.ItineraryRequest;
import org.group6.travel.domain.itinerary.model.enums.ItineraryType;
import org.group6.travel.domain.itinerary.service.ItineraryService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // 예시로 get 메서드 import
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItineraryControllerTest {

    Long tripId = 1L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItineraryService itineraryService;

    @BeforeEach
    public void beforeEach() {
        JacksonTester.initFields(this, objectMapper);
    }

    @DisplayName("여정 조회 테스트")
    @Test
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
        itineraryRequest.setDepartureLatitude(37.5123);
        itineraryRequest.setDepartureLongitude(127.0456);
        itineraryRequest.setArrivalLatitude(37.5789);
        itineraryRequest.setArrivalLongitude(127.1011);
        itineraryRequest.setPlace("Test Place");
        itineraryRequest.setLatitude(37.6123);
        itineraryRequest.setLongitude(127.2345);

        given(itineraryService.createItinerary(itineraryRequest, tripId)).willReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/api/trip/{tripId}/itinerary", tripId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itineraryRequest)))
            .andDo(print())
            .andExpectAll(status().isOk());
    }
}