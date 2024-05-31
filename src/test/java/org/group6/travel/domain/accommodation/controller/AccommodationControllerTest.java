package org.group6.travel.domain.accommodation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.group6.travel.domain.accommodation.model.dto.AccommodationDto;
import org.group6.travel.domain.accommodation.model.dto.AccommodationRequest;
import org.group6.travel.domain.accommodation.service.AccommodationService;
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
    Long accommodationId = 1L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccommodationService accommodationService;

    @BeforeEach
    public void beforeEach() {
        JacksonTester.initFields(this, objectMapper);
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
            .andExpect(jsonPath("$[0].tripId").value(tripId))
            .andExpect(jsonPath("$[0].name").value("test name"))
            .andExpect(jsonPath("$[0].latitude").value(234.23))
            .andExpect(jsonPath("$[0].longitude").value(23.23))
            .andExpect(jsonPath("$[0].checkInDatetime").exists())
            .andExpect(jsonPath("$[0].checkOutDatetime").exists());
    }

    @DisplayName("숙박 생성 테스트")
    @Test
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

        given(accommodationService.createAccommodation(tripId, accommodationRequest)).willReturn(accommodationDto);

        mvc.perform(MockMvcRequestBuilders.post("/api/trip/{tripId}/accommodation", tripId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accommodationRequest)))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.tripId").value(tripId))
            .andExpect(jsonPath("$.name").value("숙박"))
            .andExpect(jsonPath("$.latitude").value(234.23))
            .andExpect(jsonPath("$.longitude").value(23.23))
            .andExpect(jsonPath("$.checkInDatetime").exists())
            .andExpect(jsonPath("$.checkOutDatetime").exists());

    }

    @DisplayName("숙박 삭제 테스트")
    @Test
    void deleteAccommodation() throws Exception {
        // given
        doNothing().when(accommodationService).deleteAccommodation(tripId, accommodationId);

        // when & then
        mvc.perform(delete("/api/trip/{tripId}/accommodation/{accommodationId}", tripId, accommodationId))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("Deleted accommodation with id " + accommodationId));
    }
}