package org.group6.travel.domain.accommodation.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.accommodation.model.dto.AccommodationDto;
import org.group6.travel.domain.accommodation.model.dto.AccommodationRequest;
import org.group6.travel.domain.accommodation.model.entity.AccommodationEntity;
import org.group6.travel.domain.accommodation.repository.AccommodationRepository;
import org.group6.travel.domain.maps.service.MapsService;
import org.group6.travel.domain.trip.service.TripService;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final TripService tripService;
    private final MapsService mapsService;

    public List<AccommodationDto> findByTripId(Long tripId) {

        var tripEntity = tripService.getTripById(tripId);
        List<AccommodationEntity> accommodationList = accommodationRepository.findByTripEntity(tripEntity);

        return accommodationList.stream()
            .map(AccommodationDto::toAccommodationDto)
            .collect(Collectors.toList());
    }


    public AccommodationDto save(Long tripId, AccommodationRequest accommodationRequest) {

        var tripEntity = tripService.getTripById(tripId);

        // TODO : 로그인 사용자 검증 추가
        var loginUserId = '1'; // TODO : 로그인 사용자 Id 변경

        if(tripEntity.getUserId() != loginUserId) {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }

        var geocodingResult = mapsService.getLatLngFromAddress(accommodationRequest.getAddress());

        var accommodationEntity = AccommodationEntity.builder()
            .tripEntity(tripEntity)
            .name(accommodationRequest.getName())
            .checkInDatetime(accommodationRequest.getCheckInDatetime())
            .checkOutDatetime(accommodationRequest.getCheckOutDatetime())
            .lat(geocodingResult.lat)
            .lng(geocodingResult.lng)
            .build();

        var savedAccommodationEntity = accommodationRepository.save(accommodationEntity);
        return AccommodationDto.toAccommodationDto(savedAccommodationEntity);
    }


    public void delete(Long tripId, Long accommodationId) {
        // TODO : 로그인 사용자 검증 추가
        var tripUserId = tripService.getTripById(tripId).getUserId();
        var loginUserId = '1'; // TODO : 로그인 사용자 Id 변경

        if(tripUserId != loginUserId) {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }

        accommodationRepository.deleteById(accommodationId);
    }

}
