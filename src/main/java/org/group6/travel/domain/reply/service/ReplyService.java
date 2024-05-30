package org.group6.travel.domain.reply.service;

import lombok.RequiredArgsConstructor;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.accommodation.model.dto.AccommodationDto;
import org.group6.travel.domain.reply.model.dto.ReplyDto;
import org.group6.travel.domain.reply.model.entity.ReplyEntity;
import org.group6.travel.domain.reply.model.request.ReplyRequest;
import org.group6.travel.domain.reply.repository.ReplyRepository;
import org.group6.travel.domain.trip.repository.TripRepository;
import org.group6.travel.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final TripRepository tripRepository;

    public ReplyDto create(
        Long tripId, ReplyRequest replyRequest
    ) {
        var tripEntity = tripRepository.findById(tripId) //403
            .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_EXIST));

        var replyEntity = ReplyEntity.builder()
            .userId(1L)
            .tripEntity(tripEntity)
            .replyComment(replyRequest.getReplyComment())
            .createdAt(LocalDateTime.now())
            .build();

        ReplyEntity savedReplyEntity = replyRepository.save(replyEntity);

        return ReplyDto.toDto(savedReplyEntity);

    }


    @Transactional(readOnly = true)
    public List<ReplyDto> getByTripId(Long tripId) {
        var trip = tripRepository.findById(tripId)
            .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_EXIST));

       return replyRepository.findAllByTripEntity(trip)
           .stream()
           .map(ReplyDto::toDto)
           .collect(Collectors.toList());

    }

    @Transactional
    public void delete(Long tripId, Long replyId) {
        var tripEntity = tripRepository.findById(tripId)
            .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_EXIST));

        replyRepository.findById(replyId)
                .orElseThrow(()-> new ApiException(ErrorCode.REPLY_NOT_EXIST));

        replyRepository.deleteByReplyIdAndTripEntity(replyId, tripEntity)
            .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "Error"));
    }

    @Transactional
    public ReplyDto update(Long tripId, Long replyId,
                           ReplyRequest replyRequest
    ) {
        var tripEntity = tripRepository.findById(tripId)
            .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_EXIST));

        var replyEntityOptional = replyRepository.findById(replyId);

        replyEntityOptional.ifPresent(replyEntity -> {
            replyEntity.setTripEntity(tripEntity);
            replyEntity.setUserId(1L);
            replyEntity.setReplyComment(replyRequest.getReplyComment());
            replyEntity.setUpdatedAt(LocalDateTime.now());
            replyRepository.updateCommentByIdAndTripId(replyRequest.getReplyComment(), replyId, tripEntity);
        });

        return replyEntityOptional.map(ReplyDto::toDto)
            .orElseThrow(() -> new ApiException(ErrorCode.REPLY_NOT_EXIST));


    }

}
