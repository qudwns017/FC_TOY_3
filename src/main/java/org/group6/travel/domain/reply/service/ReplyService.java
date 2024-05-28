package org.group6.travel.domain.reply.service;

import lombok.RequiredArgsConstructor;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.reply.model.dto.ReplyDto;
import org.group6.travel.domain.reply.model.entity.ReplyEntity;
import org.group6.travel.domain.reply.model.request.ReplyRequest;
import org.group6.travel.domain.reply.repository.ReplyRepository;
import org.group6.travel.domain.trip.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final TripRepository tripRepository;

    public ReplyDto create(
        Long tripId, ReplyRequest replyRequest
    ) {
        var trip = tripRepository.findById(tripId);
        if (trip.isEmpty()) {
            throw new ApiException(ErrorCode.TRIP_NOT_EXIST);
        }
        var replyEntity = ReplyEntity.builder()
            .userId(tripId)
            .tripId(tripId)
            .replyComment(replyRequest.getReplyComment())
            .createdAt(LocalDateTime.now())
            .build();

        try {
            replyRepository.save(replyEntity);

        } catch (Exception e) {
            throw new ApiException(ErrorCode.SERVER_ERROR);
        }

        return ReplyDto.toDto(replyEntity);
    }


    public List<ReplyDto> getByTripId(Long tripId) {
        if (tripRepository.findById(tripId) == null) {
            throw new ApiException(ErrorCode.TRIP_NOT_EXIST);
        }
        List<ReplyEntity> replyEntityList;

        try {
            replyEntityList = replyRepository.findAllByTripId(tripId);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.SERVER_ERROR);
        }

        return replyEntityList.stream()
            .map(ReplyDto::toDto)
            .collect(Collectors.toList());
    }


    public void delete(Long tripId, Long replyId) {
        if (tripRepository.findById(tripId) == null) {
            throw new ApiException(ErrorCode.TRIP_NOT_EXIST);
        }
        if (replyRepository.findById(replyId) == null) {
            throw new ApiException(ErrorCode.REPLY_NOT_EXIST);
        }
        try {
            replyRepository.deleteByReplyIdAndTripId(replyId, tripId);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.SERVER_ERROR);
        }
    }

    public ReplyDto update(Long tripId, Long replyId,
                           ReplyRequest replyRequest
    ) {
        if (replyRepository.findByTripId(tripId) == null) {
            throw new ApiException(ErrorCode.TRIP_NOT_EXIST);
        }
        if (replyRepository.findByReplyId(replyId) == null) {
            throw new ApiException(ErrorCode.REPLY_NOT_EXIST);
        }

        var replyEntity = ReplyEntity.builder()
            .tripId(tripId)
            .replyId(replyId)
            .replyComment(replyRequest.getReplyComment())
            .updatedAt(LocalDateTime.now())
            .build();

        try {
            replyRepository.updateReply(replyEntity);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.SERVER_ERROR);
        }

        return ReplyDto.toDto(replyEntity);
    }

}

/*

  public ReplyDto update(
        Long tripId, Long replyId,
        ReplyRequest replyRequest
    ) {

        if (tripRepository.findById(tripId) == null) {
            throw new ApiException(ErrorCode.TRIP_NOT_EXIST);
        }
        if (replyRepository.findById(replyId) == null) {
            throw new ApiException(ErrorCode.REPLY_NOT_EXIST);
        }
        var replyEntity = ReplyEntity.builder()
            .tripId(tripId)
            .replyId(replyId)
            .replyComment(replyRequest.getReplyComment())
            //.createdAt()
            .updatedAt(LocalDateTime.now())
            .build();

        try {
            replyRepository.updateCommentByIdAndTripId(replyEntity, replyId, tripId);

        } catch (Exception e) {
            throw new ApiException(ErrorCode.SERVER_ERROR);
        }

        return ReplyDto.toDto(replyEntity);
    }
 */