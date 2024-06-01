
package org.group6.travel.domain.reply.service;

import org.group6.travel.domain.reply.model.dto.ReplyDto;
import org.group6.travel.domain.reply.model.entity.ReplyEntity;
import org.group6.travel.domain.reply.model.request.ReplyRequest;
import org.group6.travel.domain.reply.repository.ReplyRepository;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.model.enums.DomesticType;
import org.group6.travel.domain.trip.repository.TripRepository;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class ReplyServiceTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private ReplyRepository replyRepository;

    @InjectMocks
    ReplyService replyService;


    @BeforeAll
    static void superBeforeAll() {

    }

    Long tripId = 1L;
    Long userId = 1L;
    Long replyId = 1L;


    @Test
    @DisplayName("user")
    void compareUserIdTest() {
        TripEntity tripEntity = TripEntity.builder()
            .tripId(tripId).userId(userId)
            .tripName("tripname").startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(3))
            .likeCount(1)
            .domestic(DomesticType.DOMESTIC).tripComment("tripComment")
            .build();
        ReplyEntity replyEntity = ReplyEntity.builder().userId(userId).tripEntity(tripEntity).replyComment("replyComment").build();

        boolean result = replyService.compareUserId(userId, replyEntity);
    }

    @Test
    @DisplayName("댓글 조회 테스트")
    void getRepliesTest() {
        TripEntity tripEntity = TripEntity.builder()
            .tripId(tripId).userId(userId)
            .tripName("tripname").startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(3))
            .likeCount(1)
            .domestic(DomesticType.DOMESTIC).tripComment("tripComment")
            .build();
        ReplyEntity replyEntity = ReplyEntity.builder().userId(userId).tripEntity(tripEntity).replyComment("replyComment").build();

        List<ReplyEntity> replyEntityList = List.of(replyEntity);

        lenient().when(tripRepository.findById(tripId)).thenReturn(Optional.of(tripEntity));
        lenient().when(replyRepository.findAllByTripEntity(tripEntity)).thenReturn(replyEntityList);

        List<ReplyDto> result = replyService.getReplies(tripId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("replyComment", result.get(0).getComment());
    }

    @Test
    @DisplayName("댓글 추가 ")
    void createReplyServiceTest() {
        TripEntity tripEntity = TripEntity.builder()
            .tripId(tripId).userId(userId)
            .tripName("tripname").startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(3))
            .likeCount(1)
            .domestic(DomesticType.DOMESTIC).tripComment("tripComment")
            .build();
        ReplyEntity replyEntity = ReplyEntity.builder().userId(userId).tripEntity(tripEntity).replyComment("replyComment").build();

        lenient().when(tripRepository.findById(tripId)).thenReturn(Optional.of(tripEntity));
        lenient().when(replyRepository.save(replyEntity)).thenReturn(replyEntity);

        assertNotNull(replyService);
        assertEquals(replyEntity.getUserId(), userId);
    }

    @Test
    @DisplayName("댓글 삭제 성공 테스트")
    void deleteReplyTest() {

        TripEntity tripEntity = TripEntity.builder()
            .tripId(tripId).userId(userId)
            .tripName("tripname").startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(3))
            .likeCount(1)
            .domestic(DomesticType.DOMESTIC).tripComment("tripComment")
            .build();

        ReplyEntity replyEntity = ReplyEntity.builder().userId(userId).tripEntity(tripEntity).replyComment("replyComment").build();

        lenient().when(tripRepository.findById(tripId)).thenReturn(Optional.of(tripEntity));
        lenient().when(replyRepository.findByReplyId(replyId)).thenReturn(Optional.of(replyEntity));
        lenient().when(replyRepository.deleteByReplyIdAndTripEntity(replyId, tripEntity)).thenReturn(Optional.of(replyEntity));

        assertDoesNotThrow(() -> replyService.deleteReply(tripId, replyId, userId));

        verify(tripRepository, times(1)).findById(tripId);
        verify(replyRepository, times(1)).findByReplyId(replyId);
        verify(replyRepository, times(1)).deleteByReplyIdAndTripEntity(replyId, tripEntity);
    }

    @Test
    @DisplayName("댓글 업데이트 테스트")
    void updateReplyTest(){
        String replyComment = "comment";
        TripEntity tripEntity = TripEntity.builder()
            .tripId(tripId).userId(userId)
            .tripName("tripname").startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(3))
            .likeCount(1)
            .domestic(DomesticType.DOMESTIC).tripComment("tripComment")
            .build();

        ReplyRequest replyRequest =ReplyRequest.builder()
            .replyComment(replyComment)
            .build();

        ReplyEntity replyEntity = ReplyEntity.builder().userId(userId).tripEntity(tripEntity).replyComment(replyRequest.getReplyComment()).build();

        lenient().when(tripRepository.findById(tripId)).thenReturn(Optional.of(tripEntity));
        lenient().when(replyRepository.findByReplyId(replyId)).thenReturn(Optional.of(replyEntity));
        lenient().when(replyRepository.save(replyEntity)).thenReturn(replyEntity);

        ReplyDto update = replyService.updateReply(tripId, replyId, userId,replyRequest);

        assertEquals(replyComment, update.getComment());
        verify(tripRepository, times(1)).findById(tripId);
        verify(replyRepository, times(1)).findByReplyId(replyId);
        verify(replyRepository, times(1)).save(replyEntity);
    }
}
