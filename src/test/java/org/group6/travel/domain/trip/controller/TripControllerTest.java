package org.group6.travel.domain.trip.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.group6.travel.common.api.Api;
import org.group6.travel.domain.trip.model.dto.TripRequest;
import org.group6.travel.domain.trip.model.enums.DomesticType;
import org.group6.travel.domain.trip.service.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TripControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TripService tripService;

    @BeforeEach
    public void beforeEach(){
        JacksonTester.initFields(this, objectMapper);
    }

    @DisplayName("여행 정보 insert Test")
    @Test
    public void insertTripTest() throws Exception{
        TripRequest tripRequest = new TripRequest
                ("여행테스트", LocalDate.now(), LocalDate.now().plusDays(1L), DomesticType.OVERSEAS, "테스트용");
        given(tripService.insertTrip(any(TripRequest.class))).willReturn(null);

        mvc.perform(post("/api/trip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripRequest)))
                .andDo(print())
                .andExpectAll(status().isOk());
    }

    @Test
    public void updateTripTest() throws Exception{
        TripRequest tripRequest = new TripRequest
                ("여행테스트", LocalDate.now(), LocalDate.now().plusDays(1L), DomesticType.DOMESTIC, "테스트용");
        given(tripService.updateTrip(anyLong(),any(TripRequest.class))).willReturn(null);

        mvc.perform(put("/api/trip/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripRequest)))
                .andDo(print())
                .andExpectAll(status().isOk());
    }
}
