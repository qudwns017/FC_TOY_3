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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
        itineraryDtoList.add(itineraryDto);

        ItineraryDto itineraryDto2 = new ItineraryDto();
        itineraryDto2.setItineraryId(2L);
        itineraryDto2.setTripId(tripId);
        itineraryDto2.setItineraryName("Test Itinerary");
        itineraryDto2.setType(ItineraryType.STAY);
        itineraryDto2.setStartDatetime(LocalDateTime.now().plusHours(1));
        itineraryDto2.setEndDatetime(LocalDateTime.now().plusHours(2));
        itineraryDto2.setItineraryComment("This is a test itinerary.");
        itineraryDto2.setPlace("Test Place");
        itineraryDto2.setLatitude(37.6123);
        itineraryDto2.setLongitude(127.2345);
        itineraryDtoList.add(itineraryDto2);

        //when
        when(itineraryService.getItinerary(tripId)).thenReturn(itineraryDtoList);

        mvc.perform(MockMvcRequestBuilders.get("/api/trip/{tripId}/itinerary", tripId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tripId)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].itineraryId").value(1L))
            .andExpect(jsonPath("$.data[0].tripId").value(tripId))
            .andExpect(jsonPath("$.data[0].itineraryName").value("Test Itinerary"))
            .andExpect(jsonPath("$.data[0].type").value("MOVE"))
            .andExpect(jsonPath("$.data[0].startDatetime").exists())
            .andExpect(jsonPath("$.data[0].endDatetime").exists())
            .andExpect(jsonPath("$.data[0].itineraryComment").value("This is a test itinerary."))
            .andExpect(jsonPath("$.data[0].transportation").value("Car"))
            .andExpect(jsonPath("$.data[0].departurePlace").value("Departure Place"))
            .andExpect(jsonPath("$.data[0].arrivalPlace").value("Arrival Place"))
            .andExpect(jsonPath("$.data[0].departureLatitude").value(37.5123))
            .andExpect(jsonPath("$.data[0].departureLongitude").value(127.0456))
            .andExpect(jsonPath("$.data[0].arrivalLatitude").value(37.5789))
            .andExpect(jsonPath("$.data[0].arrivalLongitude").value(127.1011))
            .andExpect(jsonPath("$.data[1].itineraryId").value(2L))
            .andExpect(jsonPath("$.data[1].tripId").value(tripId))
            .andExpect(jsonPath("$.data[1].itineraryName").value("Test Itinerary"))
            .andExpect(jsonPath("$.data[1].type").value("STAY"))
            .andExpect(jsonPath("$.data[1].startDatetime").exists())
            .andExpect(jsonPath("$.data[1].endDatetime").exists())
            .andExpect(jsonPath("$.data[1].itineraryComment").value("This is a test itinerary."))
            .andExpect(jsonPath("$.data[1].place").value("Test Place"))
            .andExpect(jsonPath("$.data[1].latitude").value(37.6123))
            .andExpect(jsonPath("$.data[1].longitude").value(127.2345));
    }

//    @DisplayName("여정 생성 테스트")
//    @Test
//    void createItinerary() throws Exception{
//        ItineraryRequest itineraryRequest = new ItineraryRequest();
//        itineraryRequest.setItineraryName("Test Itinerary");
//        itineraryRequest.setType(ItineraryType.MOVE);
//        itineraryRequest.setStartDatetime(LocalDateTime.now().plusHours(1));
//        itineraryRequest.setEndDatetime(LocalDateTime.now().plusHours(2));
//        itineraryRequest.setItineraryComment("This is a test itinerary.");
//        itineraryRequest.setTransportation("Car");
//        itineraryRequest.setDeparturePlace("Departure Place");
//        itineraryRequest.setArrivalPlace("Arrival Place");
//        itineraryRequest.setDepartureAddress("DepartureAddress");
//        itineraryRequest.setArrivalAddress("ArrivalAddress");
//        itineraryRequest.setPlace("Test Place");
//        itineraryRequest.setStayAddress("StayAddress");
//
//        given(itineraryService.createItinerary(itineraryRequest, tripId)).willReturn(null);
//
//        mvc.perform(MockMvcRequestBuilders.post("/api/trip/{tripId}/itinerary", tripId)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(itineraryRequest)))
//            .andDo(print())
//            .andExpectAll(status().isOk());
//    }
}