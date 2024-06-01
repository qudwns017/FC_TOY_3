package org.group6.travel.domain.reply.repository;

import org.group6.travel.domain.reply.model.entity.ReplyEntity;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.model.enums.DomesticType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class ReplyRepositoryTest {

    @Mock
    ReplyRepository replyRepository;

    Long tripId = 1L;
    Long userId = 1L;
    Long replyId = 1L;

    private TripEntity createTripEntity(){
        return TripEntity.builder()
            .tripId(tripId).userId(userId)
            .tripName("tripname").startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(3))
            .likeCount(1)
            .domestic(DomesticType.DOMESTIC).tripComment("tripComment")
            .build();
    }

    private ReplyEntity createReplyEntity(TripEntity tripEntity){
        return ReplyEntity.builder().userId(userId).tripEntity(tripEntity).replyComment("replyComment").build();

    }

    @BeforeEach
    public void setUp(){
    }

    @Test
    @DisplayName("여행별 댓글 조회")
    void findAllByTripEntity() {
        TripEntity tripEntity = createTripEntity();
        ReplyEntity replyEntity1 = createReplyEntity(tripEntity);
        ReplyEntity replyEntity2 = createReplyEntity(tripEntity);
        List<ReplyEntity> replyEntityList = List.of(replyEntity1, replyEntity2);

        when(replyRepository.findAllByTripEntity(tripEntity)).thenReturn(replyEntityList);

        List<ReplyEntity> replyEntities = replyRepository.findAllByTripEntity(tripEntity);
        assertEquals(replyEntityList.size(), replyEntities.size());

    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteByReplyIdAndTripEntity() {
        TripEntity tripEntity = createTripEntity();
        ReplyEntity replyEntity =  createReplyEntity(tripEntity);

        when(replyRepository.deleteByReplyIdAndTripEntity(replyId, tripEntity)).thenReturn(Optional.of(replyEntity));

        Optional<ReplyEntity> realReplyEntityList = replyRepository.deleteByReplyIdAndTripEntity(replyId, tripEntity);
        assertEquals(replyEntity, realReplyEntityList.orElse(null));
    }

    @Test
    void findByReplyId() {
        TripEntity tripEntity =createTripEntity();
        ReplyEntity replyEntity = createReplyEntity(tripEntity);

        when(replyRepository.findByReplyId(replyId)).thenReturn(Optional.of(replyEntity));

        Optional<ReplyEntity>foundReply = replyRepository.findByReplyId(replyId);

        assertEquals(replyEntity, foundReply.orElse(null));
    }
}