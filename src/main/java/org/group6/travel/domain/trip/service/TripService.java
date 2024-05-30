package org.group6.travel.domain.trip.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.like.model.entity.LikeEntity;
import org.group6.travel.domain.like.repository.LikeRepository;
import org.group6.travel.domain.trip.model.dto.TripDto;
import org.group6.travel.domain.trip.model.dto.TripRequest;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripService {
    private final TripRepository tripRepository;
    private final LikeRepository likeRepository;

    public boolean compareUserId(Long userId, TripEntity trip){
        return trip.getUserId().equals(userId);
    }

    public TripDto insertTrip(TripRequest tripRequest){
        return TripDto.toDto(tripRepository.save(TripEntity.builder()
                .userId((long)1)
                .tripName(tripRequest.getTripName())
                .startDate(tripRequest.getStartDate())
                .endDate(tripRequest.getEndDate())
                .domestic(tripRequest.getDomestic())
                .likeCount(0)
                .tripComment(tripRequest.getTripComment())
                .build())
            );
    }

    @Transactional(readOnly = true)
    public List<TripDto> getTripAll(){
        return tripRepository.findAll().stream()
                .map(TripDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TripEntity getTripById(Long tripId){
        return tripRepository.findById(tripId)
                .orElseThrow(()->new ApiException(ErrorCode.TRIP_NOT_EXIST));
    }

    @Transactional(readOnly = true)
    public List<TripEntity> getTripByUserId(Long userId){
        return tripRepository.findByUserId(userId).get();
    }

    @Transactional(readOnly = true)
    public List<TripDto> getTripByKeyword(String keyword){
        return tripRepository.findByTripNameContains(keyword).stream()
                .map(TripDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TripEntity> getTripByLike(Long userId){
        List<Long> tripIdList = likeRepository.findByUserId(userId);
        return tripRepository.findByLikeList(tripIdList);
    }

    @Transactional
    public TripEntity updateTrip(Long tripId, TripRequest tripRequest){
        TripEntity entity = tripRepository.findById(tripId)
                .orElseThrow(()->new ApiException(ErrorCode.TRIP_NOT_EXIST));

        entity.setTripName(tripRequest.getTripName());
        entity.setStartDate(tripRequest.getStartDate());
        entity.setEndDate(tripRequest.getEndDate());
        entity.setDomestic(tripRequest.getDomestic());
        entity.setTripComment(tripRequest.getTripComment());

        return tripRepository.save(entity);

    }

    @Transactional
    public void deleteTrip(Long tripId){
        TripEntity entity = tripRepository.findById(tripId)
                .orElseThrow(()->new ApiException(ErrorCode.TRIP_NOT_EXIST));

        tripRepository.delete(entity);

    }
}
