package org.group6.travel.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.error.SuccessCode;
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

    @Transactional(readOnly = true)
    public List<LikeEntity> getlikes() {
        return likeRepository.findAll();
    }

    public LikeDto addLike(Long tripId) {
        var tripEntity = tripRepository.findById(tripId)
            .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_EXIST));

        //존재 확인
        Optional<LikeEntity> existingLikeOptional = likeRepository.findByTripId(tripId);

        if (existingLikeOptional.isPresent()) {
            LikeEntity existingLike = existingLikeOptional.get();
            likeRepository.delete(existingLike);
            tripRepository.decrementLikeCount(tripEntity.getTripId());
            throw new ApiException(SuccessCode.UNLIKE);
            // 삭제
        } else {
            LikeEntity newLike = LikeEntity.builder()
                .userId(1L)
                .tripId(tripId)
                .build();
            newLike = likeRepository.save(newLike);
            tripRepository.incrementLikeCount(tripEntity.getTripId());
            return LikeDto.toDto(newLike);
        }
    }

    @Transactional(readOnly = true)
    public List<LikeEntity> all() {
        return likeRepository.findAll();
    }

}
