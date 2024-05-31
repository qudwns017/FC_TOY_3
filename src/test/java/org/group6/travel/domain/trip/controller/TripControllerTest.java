package org.group6.travel.domain.trip.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.group6.travel.domain.trip.model.dto.TripDto;
import org.group6.travel.domain.trip.model.enums.DomesticType;
import org.group6.travel.domain.trip.service.TripService;
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
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TripControllerTest {

    Long userId = 1L;
    int  tripId = 1;
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

    @Test
    @DisplayName("여행 리스트 조회 테스트")
    void getTrips() throws Exception {
        //given
        List<TripDto> tripDtoList = new ArrayList<>();
        TripDto tripDto = new TripDto((long)tripId, userId,"tripname", LocalDate.now(), LocalDate.now().plusDays(10), DomesticType.OVERSEAS,1,"comment");
        tripDtoList.add(tripDto);

        //when
        when(tripService.getTrips()).thenReturn(tripDtoList);

        mvc.perform(MockMvcRequestBuilders.get("/api/trip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tripDtoList))) // tripDtoList를 전달해야 함
            .andDo(print())
            .andExpect(status().isOk());
    }
}