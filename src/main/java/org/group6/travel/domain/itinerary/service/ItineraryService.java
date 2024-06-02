package org.group6.travel.domain.itinerary.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.itinerary.model.dto.ItineraryDto;
import org.group6.travel.domain.itinerary.model.dto.ItineraryRequest;
import org.group6.travel.domain.itinerary.model.entity.ItineraryEntity;
import org.group6.travel.domain.itinerary.model.entity.MoveEntity;
import org.group6.travel.domain.itinerary.model.entity.StayEntity;
import org.group6.travel.domain.itinerary.model.enums.ItineraryType;
import org.group6.travel.domain.itinerary.repository.ItineraryRepository;
import org.group6.travel.domain.itinerary.repository.MoveRepository;
import org.group6.travel.domain.itinerary.repository.StayRepository;
import org.group6.travel.domain.maps.service.MapsService;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.repository.TripRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final MoveRepository moveRepository;
    private final StayRepository stayRepository;
    private final TripRepository tripRepository;
    private final MapsService mapsService;

    public boolean compareUserId(Long userId, TripEntity trip) {return trip.getUserId().equals(userId);}

    @Transactional(readOnly = true)
    public List<ItineraryDto> getItinerary(Long tripId) {
        var tripEntity = tripRepository.findByTripId(tripId)
            .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_EXIST));

        return itineraryRepository.findAllByTripEntity(tripEntity)
            .stream()
            .map(this::mapToItineraryDto)
            .collect(Collectors.toList());
    }

    private ItineraryDto mapToItineraryDto(ItineraryEntity itinerary) {
        MoveEntity move = itinerary.getType().equals(ItineraryType.MOVE) ? moveRepository.findFirstByItineraryId(itinerary.getItineraryId()) : null;
        StayEntity stay = itinerary.getType().equals(ItineraryType.STAY) ? stayRepository.findFirstByItineraryId(itinerary.getItineraryId()) : null;

        return ItineraryDto.toDto(move, stay);
    }

    public ItineraryDto createItinerary(
        ItineraryRequest itineraryRequest,
        Long tripId,
        Long userId
    ) {
        var tripEntity = tripRepository.findByTripId(tripId)
            .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_EXIST));

        if(!compareUserId(userId, tripEntity)) {
            throw new ApiException(ErrorCode.USER_NOT_MATCH);
        }
        if(!isValidDateTime(
            tripEntity.getStartDate(), tripEntity.getEndDate(), itineraryRequest.getStartDatetime(), itineraryRequest.getEndDatetime()
        )){
            throw new ApiException(ErrorCode.TIME_ERROR, "여행 시간 범위에 들어가지 않습니다.");
        }

        MoveEntity moveEntity = null;
        StayEntity stayEntity = null;

        return saveItineraryByType(null,itineraryRequest,tripEntity,moveEntity,stayEntity);
    }

    public ItineraryDto saveItineraryByType(Long itineraryId, ItineraryRequest itineraryRequest, TripEntity tripEntity, MoveEntity moveEntity, StayEntity stayEntity){
        if(itineraryRequest.getType().getValue()==0){

            var geocodingResultDeparture = mapsService.getLatLngFromAddress(itineraryRequest.getDepartureAddress());
            var geocodingResultArrival = mapsService.getLatLngFromAddress(itineraryRequest.getArrivalAddress());

            moveEntity = MoveEntity.builder()
                    .itineraryId(itineraryId)
                    .tripEntity(tripEntity)
                    .itineraryName(itineraryRequest.getItineraryName())
                    .type(itineraryRequest.getType())
                    .startDatetime(itineraryRequest.getStartDatetime())
                    .endDatetime(itineraryRequest.getEndDatetime())
                    .itineraryComment(itineraryRequest.getItineraryComment())
                    .transportation(itineraryRequest.getTransportation())
                    .departurePlace(itineraryRequest.getDeparturePlace())
                    .arrivalPlace(itineraryRequest.getArrivalPlace())
                    .departureLatitude(geocodingResultDeparture.lat)
                    .departureLongitude(geocodingResultDeparture.lng)
                    .arrivalLatitude(geocodingResultArrival.lat)
                    .arrivalLongitude(geocodingResultArrival.lng)
                    .build();
            moveRepository.save(moveEntity);
        }
        else{
            var geocodingResult = mapsService.getLatLngFromAddress(itineraryRequest.getStayAddress());

            stayEntity = StayEntity.builder()
                    .itineraryId(itineraryId)
                    .tripEntity(tripEntity)
                    .itineraryName(itineraryRequest.getItineraryName())
                    .type(itineraryRequest.getType())
                    .startDatetime(itineraryRequest.getStartDatetime())
                    .endDatetime(itineraryRequest.getEndDatetime())
                    .itineraryComment(itineraryRequest.getItineraryComment())
                    .place(itineraryRequest.getPlace())
                    .latitude(geocodingResult.lat)
                    .longitude(geocodingResult.lng)
                    .build();
            stayRepository.save(stayEntity);
        }
        return ItineraryDto.toDto(moveEntity, stayEntity);
    }

    @Transactional
    public ItineraryDto updateItinerary(
            Long tripId,
            Long itineraryId,
            Long userId,
            ItineraryRequest itineraryRequest
    ){
        var tripEntity = tripRepository.findByTripId(tripId)
            .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_EXIST));

        if(!compareUserId(userId, tripEntity)){
            throw new ApiException(ErrorCode.USER_NOT_MATCH);
        }

        if(!isValidDateTime(
            tripEntity.getStartDate(), tripEntity.getEndDate(), itineraryRequest.getStartDatetime(), itineraryRequest.getEndDatetime()
        )){
            throw new ApiException(ErrorCode.TIME_ERROR, "여행 시간 범위에 들어가지 않습니다.");
        }

        var itineraryEntity = itineraryRepository.findById(itineraryId)
                .orElseThrow(()->new ApiException(ErrorCode.ITINERARY_NOT_EXIST, "Itinerary not found"));

        MoveEntity moveEntity = moveRepository.findById(itineraryId)
                .orElse(null);
        StayEntity stayEntity = stayRepository.findById(itineraryId)
                .orElse(null);

        if(!itineraryEntity.getType().equals(itineraryRequest.getType())){
            moveRepository.deleteById(itineraryId);
            stayRepository.deleteById(itineraryId);
        }
        return saveItineraryByType(itineraryId, itineraryRequest, tripEntity, moveEntity, stayEntity);

    }


    @Transactional
    public void deleteItinerary(Long tripId, Long itineraryId, Long userId) {

        var tripEntity = tripRepository.findByTripId(tripId)
            .orElseThrow(() -> new ApiException(ErrorCode.TRIP_NOT_EXIST));

        if(!compareUserId(userId, tripEntity)){
            throw new ApiException(ErrorCode.USER_NOT_MATCH);
        }
        if (!itineraryRepository.existsById(itineraryId)) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "Itinerary not found");
        } else {
            itineraryRepository.deleteById(itineraryId);
        }
    }

    public static boolean isValidDateTime(
        LocalDate travelStartDate, LocalDate travelEndDate,
        LocalDateTime itineraryStartDatetime, LocalDateTime itineraryEndDatetime
    ) {
        return (itineraryStartDatetime.toLocalDate().isEqual(travelStartDate) || itineraryStartDatetime.toLocalDate().isAfter(travelStartDate)) &&
            (itineraryEndDatetime.toLocalDate().isEqual(travelEndDate) || itineraryEndDatetime.toLocalDate().isBefore(travelEndDate)) &&
            (itineraryStartDatetime.isBefore(itineraryEndDatetime));
    }
}
