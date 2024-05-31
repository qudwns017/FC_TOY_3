package org.group6.travel.domain.reply.service;

import lombok.RequiredArgsConstructor;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.reply.model.dto.ReplyDto;
import org.group6.travel.domain.reply.model.entity.ReplyEntity;
import org.group6.travel.domain.reply.model.request.ReplyRequest;
import org.group6.travel.domain.reply.repository.ReplyRepository;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.repository.TripRepository;
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

    public boolean compareUserId(Long userId, ReplyEntity reply){
        return reply.getUserId().equals(userId);
    }


    @Transactional(readOnly = true)
    public List<ReplyDto> getReplies(Long tripId) {
        var trip = tripRepository.findById(tripId)
                .orElseThrow(()->new ApiException(ErrorCode.TRIP_NOT_EXIST));

        List<ReplyEntity> replyEntityList;

        try {
            replyEntityList = replyRepository.findAllByTripEntity(trip);
        } catch (Exception e) {
            // TODO error 수정하기
            throw new ApiException(ErrorCode.SERVER_ERROR);
        }

        return replyEntityList
            .stream()
            .map(ReplyDto::toDto)
            .collect(Collectors.toList());

    }

    public ReplyDto createReply(
        Long tripId, Long userId, ReplyRequest replyRequest
    ) {
        var tripEntity = tripRepository.findById(tripId)
            .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_EXIST));

        var replyEntity = ReplyEntity.builder()
            .userId(userId)
            .tripEntity(tripEntity)
            .replyComment(replyRequest.getReplyComment())
            //.createdAt(LocalDateTime.now())
            .build();

        try {
            replyRepository.save(replyEntity);

        } catch (Exception e) {
            throw new ApiException(ErrorCode.SERVER_ERROR);
        }

        return ReplyDto.toDto(replyEntity);
    }

    @Transactional
    public ReplyDto updateReply(Long tripId, Long replyId, Long userId,
        ReplyRequest replyRequest
    ) {
        var tripEntity = tripRepository.findById(tripId)
            .orElseThrow(()->new ApiException(ErrorCode.TRIP_NOT_EXIST));
        var replyEntity = replyRepository.findByReplyId(replyId)
                .orElseThrow(()->new ApiException(ErrorCode.REPLY_NOT_EXIST));

        if(!compareUserId(userId, replyEntity)){
            throw new ApiException(ErrorCode.USER_NOT_MATCH);
        }

        replyEntity.setReplyComment(replyRequest.getReplyComment());
        //replyEntity.setUpdatedAt(LocalDateTime.now());
        return ReplyDto.toDto(replyRepository.save(replyEntity));
    }

    @Transactional
    public void deleteReply(Long tripId, Long replyId, Long userId) {
        var tripEntity = tripRepository.findById(tripId)
                .orElseThrow(()->new ApiException(ErrorCode.TRIP_NOT_EXIST));
        var replyEntity = replyRepository.findByReplyId(replyId)
                .orElseThrow(()->new ApiException(ErrorCode.REPLY_NOT_EXIST));

        if(!compareUserId(userId, replyEntity)){
            throw new ApiException(ErrorCode.USER_NOT_MATCH);
        }

        replyRepository.deleteByReplyIdAndTripEntity(replyId, tripEntity)
                .orElseThrow(()->new ApiException(ErrorCode.REPLY_NOT_EXIST, "Error"));
    }
}
