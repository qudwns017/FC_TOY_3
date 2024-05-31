
package org.group6.travel.domain.accommodation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.group6.travel.domain.accommodation.model.dto.AccommodationDto;
import org.group6.travel.domain.accommodation.model.dto.AccommodationRequest;
import org.group6.travel.domain.accommodation.model.entity.AccommodationEntity;
import org.group6.travel.domain.accommodation.service.AccommodationService;
import org.group6.travel.domain.reply.service.ReplyService;
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


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccommodationControllerTest {

    Long tripId = 1L;
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

   /* @DisplayName("숙박 생성 테스트")
    @Test
    void createAccommodation() throws Exception {
        AccommodationRequest accommodationRequest = new AccommodationRequest("숙박", LocalDateTime.now(), LocalDateTime.now().plusDays(3), "주소");
        given(accommodationService.createAccommodation(tripId, accommodationRequest)).willReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/api/trip/{tripId}/accommodation", tripId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accommodationRequest)))
            .andDo(print())
            .andExpect(status().isOk());

    }*/


    @DisplayName("숙박 조회 테스트")
    @Test //성공
    void getAccommodationList() throws Exception{
        //given
        List<AccommodationDto> accommodationDtoList = new ArrayList<>();
        AccommodationDto accommodationDto = new AccommodationDto().builder()
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
            .andExpectAll(status().isOk());


    }
}
