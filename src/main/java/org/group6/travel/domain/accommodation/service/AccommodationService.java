package org.group6.travel.domain.accommodation.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.group6.travel.domain.accommodation.model.dto.AccommodationDto;
import org.group6.travel.domain.accommodation.model.dto.AccommodationRequest;
import org.group6.travel.domain.accommodation.model.entity.AccommodationEntity;
import org.group6.travel.domain.accommodation.repository.AccommodationRepository;
import org.group6.travel.domain.trip.service.TripService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final TripService tripService;

    public List<AccommodationDto> findByTripId(Long tripId) {
        // TODO : TripId 검증 추가
        // TODO : 로그인 사용자 검증 추가

        List<AccommodationEntity> accommodationList = accommodationRepository.findByTripId(tripId);

        return accommodationList.stream()
            .map(AccommodationDto::toAccommodationDto)
            .collect(Collectors.toList());
    }


    public AccommodationDto save(Long tripId, AccommodationRequest accommodationRequest) {
        // TODO : 로그인 사용자 검증 추가

        var accommodationEntity = AccommodationEntity.builder()
            .tripId(tripId)
            .name(accommodationRequest.getName())
            .checkInDatetime(accommodationRequest.getCheckInDatetime())
            .checkOutDatetime(accommodationRequest.getCheckOutDatetime())
            .lat(accommodationRequest.getLat())
            .lng(accommodationRequest.getLng())
            .build();

        var savedAccommodationEntity = accommodationRepository.save(accommodationEntity);
        return AccommodationDto.toAccommodationDto(savedAccommodationEntity);
    }


    public void delete(Long tripId, Long accommodationId) {

        // TODO : 로그인 사용자 검증 추가
        accommodationRepository.deleteById(accommodationId);
    }


}
