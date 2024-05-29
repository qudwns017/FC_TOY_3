package org.group6.travel.domain.reply.service;

import lombok.RequiredArgsConstructor;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.reply.model.dto.ReplyDto;
import org.group6.travel.domain.reply.model.entity.ReplyEntity;
import org.group6.travel.domain.reply.model.request.ReplyRequest;
import org.group6.travel.domain.reply.repository.ReplyRepository;
import org.group6.travel.domain.trip.repository.TripRepository;
import org.group6.travel.domain.trip.service.TripService;
import org.group6.travel.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final TripService tripService;

    public ReplyDto create(
        Long tripId, ReplyRequest replyRequest
    ) {
        var trip = tripRepository.findById(tripId)
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST,"Trip not found"));

        var replyEntity = ReplyEntity.builder()
            .userId(1L)
            .tripEntity(trip)
            .replyComment(replyRequest.getReplyComment())
            .createdAt(LocalDateTime.now())
            //.updatedAt(LocalDateTime.now())
            .build();

        try {
            replyRepository.save(replyEntity);

        } catch (Exception e) {
            throw new ApiException(ErrorCode.SERVER_ERROR);
        }

        return ReplyDto.toDto(replyEntity);
    }


    public List<ReplyDto> getByTripId(Long tripId) {
        var trip = tripRepository.findById(tripId)
                .orElseThrow(()->new ApiException(ErrorCode.TRIP_NOT_EXIST));

        List<ReplyEntity> replyEntityList;

        try {
            replyEntityList = replyRepository.findAllByTripEntity(trip);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.SERVER_ERROR);
        }

        return replyEntityList.stream()
            .map(ReplyDto::toDto)
            .collect(Collectors.toList());
    }

   @Transactional
    public void delete(Long tripId, Long replyId) {
        var trip = tripRepository.findById(tripId)
                .orElseThrow(()->new ApiException(ErrorCode.TRIP_NOT_EXIST));
        var reply = replyRepository.findByReplyId(replyId);

        if(reply == null){
            throw new ApiException(ErrorCode.REPLY_NOT_EXIST);
        }

        replyRepository.deleteByReplyIdAndTripEntity(replyId, trip)
                .orElseThrow(()->new ApiException(ErrorCode.REPLY_NOT_EXIST, "Error"));

    }

    @Transactional
    public ReplyDto update(Long tripId, Long replyId,
                           ReplyRequest replyRequest
    ) {
        var trip = tripRepository.findById(tripId)
                .orElseThrow(()->new ApiException(ErrorCode.TRIP_NOT_EXIST));
        var reply = replyRepository.findByReplyId(replyId);

        if(reply == null){
            throw new ApiException(ErrorCode.REPLY_NOT_EXIST);
        }

        var replyEntity  = ReplyEntity.builder()
                 .replyId(replyId)
                 .tripEntity(trip)
                 .userId(1L)
                 .replyComment(replyRequest.getReplyComment())
                 .updatedAt(LocalDateTime.now())
                 .createdAt(reply.getCreatedAt())
                 .build();

        replyRepository.updateCommentByIdAndTripId(replyEntity.getReplyComment(), replyId, trip);

        return ReplyDto.toDto(replyEntity);
    }

}
