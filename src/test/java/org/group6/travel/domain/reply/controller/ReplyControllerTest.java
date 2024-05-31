package org.group6.travel.domain.reply.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.group6.travel.domain.reply.model.dto.ReplyDto;
import org.group6.travel.domain.reply.model.entity.ReplyEntity;
import org.group6.travel.domain.reply.model.request.ReplyRequest;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReplyControllerTest {

    Long tripId = 1L;

    @LocalServerPort
    private int port;
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReplyService replyService;

    @BeforeEach
    public void beforeEach() {
        JacksonTester.initFields(this, objectMapper);
    }

    @DisplayName("댓글 작성 컨트롤러 테스트 ")
    @Test
    public void createReplyTest() throws Exception {
        ReplyRequest replyRequest = new ReplyRequest("댓글테스트");
        given(replyService.createReply(tripId, replyRequest)).willReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/api/trip/{trip_id}/reply", tripId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(replyRequest)))
            .andDo(print())
            .andExpectAll(status().isOk());
    }

    @DisplayName("댓글 조회 컨트롤러 테스트")
    @Test
    public void getRepliesTest() throws Exception {
        // Given
        Long tripId = 1L;
        List<ReplyDto> replyDtoList = new ArrayList<>();
        ReplyDto replyDto = new ReplyDto(tripId,1L, 2L, "comment",LocalDateTime.now(),LocalDateTime.now());
        replyDtoList.add(replyDto);

        // When & Then
        when(replyService.getReplies(tripId)).thenReturn(replyDtoList);

        mvc.perform(MockMvcRequestBuilders.get("/api/trip/{trip_id}/reply", tripId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tripId)))
            .andDo(log())
            .andExpectAll(status().isOk());
    }
}