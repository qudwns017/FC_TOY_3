package org.group6.travel.domain.accommodation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.accommodation.model.dto.AccommodationDto;
import org.group6.travel.domain.accommodation.model.dto.AccommodationRequest;
import org.group6.travel.domain.accommodation.model.entity.AccommodationEntity;
import org.group6.travel.domain.accommodation.repository.AccommodationRepository;
import org.group6.travel.domain.trip.repository.TripRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final TripRepository tripRepository;

    public List<AccommodationDto> findByTripId(Long tripId) {
        // TODO : TripId 검증 추가

        /*
        if (tripId == null) {

        }
         */

        // TODO : 로그인 사용자 검증 추가

        var tripEntity = tripRepository.findByTripId(tripId);
        List<AccommodationEntity> accommodationList = accommodationRepository.findByTripEntity(tripEntity);

        return accommodationList.stream()
            .map(AccommodationDto::toAccommodationDto)
            .collect(Collectors.toList());
    }


    public AccommodationDto save(Long tripId, AccommodationRequest accommodationRequest) {
        // TODO : 로그인 사용자 검증 추가

        var tripEntity = tripRepository.findByTripId(tripId);
        var tripEntity = tripService.getTripById(tripId);

        if(!isValidDateTime(
            tripEntity.getStartDate(), tripEntity.getEndDate(), accommodationRequest.getCheckInDatetime(), accommodationRequest.getCheckOutDatetime()
        )){
            throw new ApiException(ErrorCode.TIME_ERROR, "여행 시간 범위에 들어가지 않습니다.");
        }

        var accommodationEntity = AccommodationEntity.builder()
            .tripEntity(tripEntity)
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

    public static boolean isValidDateTime(
        LocalDate startTravel, LocalDate endTravel,
        LocalDateTime checkIn, LocalDateTime checkOut
    ) {
        return (checkIn.toLocalDate().isEqual(startTravel) || checkIn.toLocalDate().isAfter(startTravel)) &&
            (checkOut.toLocalDate().isEqual(endTravel) || checkOut.toLocalDate().isBefore(endTravel)) &&
            (checkIn.isBefore(checkOut));
    }

}
