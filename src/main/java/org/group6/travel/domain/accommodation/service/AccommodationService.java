package org.group6.travel.domain.accommodation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.group6.travel.common.status.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.accommodation.model.dto.AccommodationDto;
import org.group6.travel.domain.accommodation.model.dto.AccommodationRequest;
import org.group6.travel.domain.accommodation.model.entity.AccommodationEntity;
import org.group6.travel.domain.accommodation.repository.AccommodationRepository;
import org.group6.travel.domain.maps.service.MapsService;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.repository.TripRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final TripRepository tripRepository;
    private final MapsService mapsService;

    public boolean compareUserId(Long userId, TripEntity trip) {return userId.equals(trip.getUserId());}

    @Transactional(readOnly = true)
    public List<AccommodationDto> getAccommodationList(Long tripId) {
        var tripEntity = tripRepository.findByTripId(tripId)
            .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_EXIST));

        return accommodationRepository.findByTripEntity(tripEntity)
            .stream()
            .map(AccommodationDto::toAccommodationDto)
            .collect(Collectors.toList());
    }

    public AccommodationDto createAccommodation(Long tripId, Long userId, AccommodationRequest accommodationRequest) {

        var tripEntity = tripRepository.findByTripId(tripId)
            .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_EXIST));

        if(!compareUserId(userId, tripEntity)) {
            throw new ApiException(ErrorCode.USER_NOT_MATCH);
        }

        if(!isValidDateTime(
            tripEntity.getStartDate(), tripEntity.getEndDate(), accommodationRequest.getCheckInDatetime(), accommodationRequest.getCheckOutDatetime()
        )){
            throw new ApiException(ErrorCode.TIME_ERROR, "여행 시간 범위에 들어가지 않습니다.");
        }

        var geocodingResult = mapsService.getLatLngFromAddress(accommodationRequest.getAddress());

        var accommodationEntity = AccommodationEntity.builder()
            .tripEntity(tripEntity)
            .name(accommodationRequest.getName())
            .checkInDatetime(accommodationRequest.getCheckInDatetime())
            .checkOutDatetime(accommodationRequest.getCheckOutDatetime())
            .latitude(geocodingResult.lat)
            .longitude(geocodingResult.lng)
            .build();

        return Optional.of(accommodationRepository.save(accommodationEntity))
            .map(AccommodationDto::toAccommodationDto)
            .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "잘못된 서식입니다."));
    }

    @Transactional
    public void deleteAccommodation(Long tripId, Long accommodationId, Long userId) {

        var tripEntity = tripRepository.findByTripId(tripId)
            .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_EXIST));

        if(!compareUserId(userId, tripEntity)) {
            throw new ApiException(ErrorCode.USER_NOT_MATCH);
        }

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