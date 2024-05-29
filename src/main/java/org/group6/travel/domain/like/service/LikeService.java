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

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public LikeDto like(Long tripId) {
        var trip = tripRepository.findById(tripId)
            .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_EXIST));

        //존재 확인
        LikeEntity existingLike = likeRepository.findByTripId(tripId);

        if (existingLike == null) {
            LikeEntity newLike = LikeEntity.builder()
                .userId(trip.getUserId())
                .tripId(tripId)
                .build();
            likeRepository.save(newLike);

        } else {
            likeRepository.delete(existingLike);
            //삭제
        }

        return null; //LikeDto.toDto(existingLike)
    }

    public List<LikeEntity> all() {
        return likeRepository.findAll();
    }

}
/*


 LikeEntity clickLike = likeRepository.findUserIdAndTripId(tripId,userId);

if(clickLike == null){
            LikeEntity likeEntity = LikeDto.builder()
                .tripId(tripId)
                .userId(userId)
                .build();
                likeRepository.save(likeEntity);
        }else{
            likeRepository.deleteById(clickLike.getTripId()); //id
        }

         var likeEntity = LikeEntity.builder()
            .userId(userId)
            .tripId(tripId)
            .build();

        likeRepository.save(likeEntity);

        if (likeEntity == null) {
            //기존 데이터 없다면 좋아요 생성
        } else {
            //있다면 좋아요 취소
        }

 */