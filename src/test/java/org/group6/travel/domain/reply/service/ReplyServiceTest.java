package org.group6.travel.domain.reply.service;

import org.group6.travel.common.api.ResponseApi;
import org.group6.travel.domain.reply.model.dto.ReplyDto;
import org.group6.travel.domain.reply.model.entity.ReplyEntity;
import org.group6.travel.domain.reply.model.request.ReplyRequest;
import org.group6.travel.domain.reply.repository.ReplyRepository;
import org.group6.travel.domain.trip.model.dto.TripDto;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.model.enums.DomesticType;
import org.group6.travel.domain.trip.repository.TripRepository;
import org.group6.travel.domain.user.model.converter.UserConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReplyServiceTest {



    @Mock
    private TripRepository tripRepository;

    @Mock
    private ReplyRepository replyRepository;

    @InjectMocks
    private ReplyService replyService;
    @Autowired
    private UserConverter userConverter;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("댓글 추가 ")
    void createReply() {
        Long userId = 1L;
        Long tripId = 1L;
        Long replyId = 1L;

        String replyComment = "test reply";

        TripEntity tripEntity = TripEntity.builder()
            .tripId(tripId).userId(userId)
            .tripName("tripname").startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(10))
            .domestic(DomesticType.OVERSEAS).tripComment("TripComment")
            .build();

        ReplyEntity replyEntity = ReplyEntity.builder().replyId(replyId).userId(userId).tripEntity(tripEntity).build();
        ReplyRequest replyRequest = ReplyRequest.builder()
            .replyComment(replyComment)
            .build();

        //when (userConverter.getUser(userId)).thenReturn(user); 사용자 인증
        when(tripRepository.findById(tripId)).thenReturn(Optional.of(tripEntity));
        when(replyRepository.save(replyEntity)).thenReturn(replyEntity);

        ReplyDto replyDto = replyService.createReply(tripId, replyRequest);

        assertNotNull(replyDto);
        assertEquals(replyComment, replyDto.getComment());
        //assertEquals(userId, replyDto.getUserId());
        assertEquals(tripId, replyDto.getTripId());

    }
}

/*



 // Arrange
        TripEntity tripEntity = new TripEntity();
        tripEntity.setTripId(tripId);
        tripEntity.setUserId(userId);
        tripEntity.setTripName("tripName");
        tripEntity.setStartDate(LocalDate.now());
        tripEntity.setEndDate(LocalDate.now().plusDays(2));
        tripEntity.setDomestic(DomesticType.DOMESTIC);
        tripEntity.setTripComment("tripComment");
        tripEntity.setLikeCount(1);
        tripEntity.setItineraryList(new ArrayList<>());
        tripEntity.setAccommodationList(new ArrayList<>());
        tripEntity.setReplyList(new ArrayList<>());

        ReplyEntity replyEntity= new ReplyEntity(replyId, userId,tripEntity,"comment",LocalDateTime.now(), LocalDateTime.now());
        List<ReplyEntity> replyEntityList = List.of(replyEntity);

        when(tripRepository.findById(tripId)).thenReturn(Optional.of(tripEntity));
        when(replyRepository.findAllByTripEntity(tripEntity)).thenReturn(replyEntityList);

        // Act
        List<ReplyDto> result = replyService.getReplies(tripId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(tripRepository, times(1)).findById(tripId);
        verify(replyRepository, times(1)).findAllByTripEntity(tripEntity);


--

TripEntity tripEntity = new TripEntity();
        tripEntity.setTripId(1L);
        tripEntity.setUserId(1L);
        tripEntity.setTripName("tripName");
        tripEntity.setStartDate(LocalDate.now());
        tripEntity.setEndDate(LocalDate.now().plusDays(2));
        tripEntity.setDomestic(DomesticType.DOMESTIC);
        tripEntity.setTripComment("tripComment");
        tripEntity.setLikeCount(1);
        tripEntity.setItineraryList(new ArrayList<>());
        tripEntity.setAccommodationList(new ArrayList<>());
        tripEntity.setReplyList(new ArrayList<>());

        ReplyEntity replyEntity= new ReplyEntity(1L, 1L,tripEntity,"comment",LocalDateTime.now(), LocalDateTime.now());
        List<ReplyEntity> replyEntityList = List.of(replyEntity);

        ReplyRequest replyRequest = ReplyRequest.builder()
            .replyComment("test comment")
            .build();

        ReplyDto replyDto = ReplyDto.builder()
            .tripId(tripEntity.getTripId())
            .comment(replyRequest.getReplyComment())
            .id(1L)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now().plusHours(2))
            .build();


        when(tripRepository.findById(tripEntity.getTripId())).thenReturn(Optional.of(tripEntity));
        when(replyRepository.save(any(ReplyEntity.class))).thenReturn(null);
         replyDto = replyService.createReply(tripEntity.getTripId(), replyRequest);

        assertNotNull(replyDto);
        assertEquals(tripEntity.getTripId(), replyDto.getTripId());
        assertEquals(replyRequest.getReplyComment(),replyDto.getComment());
 */