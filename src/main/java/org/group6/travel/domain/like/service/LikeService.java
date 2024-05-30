package org.group6.travel.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.group6.travel.common.api.Api;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.like.model.dto.LikeDto;
import org.group6.travel.domain.like.model.entity.LikeEntity;
import org.group6.travel.domain.like.repository.LikeRepository;
import org.group6.travel.domain.trip.repository.TripRepository;
import org.group6.travel.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public LikeDto like(Long tripId) {
        var tripEntity = tripRepository.findById(tripId)
            .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_EXIST));

        //존재 확인
        LikeEntity existingLike = likeRepository.findByTripId(tripId)
                .orElse(null);

        if (existingLike == null) {
            LikeEntity newLike = LikeEntity.builder()
                .userId(1L)
                .tripId(tripId)
                .build();
            newLike = likeRepository.save(newLike);
            return LikeDto.toDto(newLike);

        } else {
            likeRepository.delete(existingLike);
            return null;
            //삭제
        }
    }

    @Transactional(readOnly = true)
    public List<LikeEntity> all() {
        return likeRepository.findAll();
    }

}