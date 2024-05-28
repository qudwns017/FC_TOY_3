package org.group6.travel.domain.itinerary.service;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final MoveRepository moveRepository;
    private final StayRepository stayRepository;

    public List<ItineraryDto> getItinerary(Long tripId) {
//        if (tripService.getTripId(tripId) == null) {
//            throw new ApiException(ErrorCode.NULL_POINT, "없는 여행 아이디입니다.");
//        }
        List<ItineraryEntity> itineraries = itineraryRepository.findAllByTripId(tripId);

        return itineraries.stream()
            .map(this::mapToItineraryDto)
            .collect(Collectors.toList());
    }

    private ItineraryDto mapToItineraryDto(ItineraryEntity itinerary) {
        MoveEntity move = itinerary.getType().equals(ItineraryType.MOVE) ? moveRepository.findFirstByItineraryId(itinerary.getItineraryId()) : null;
        StayEntity stay = itinerary.getType().equals(ItineraryType.STAY) ? stayRepository.findFirstByItineraryId(itinerary.getItineraryId()) : null;

        return ItineraryDto.toDto(itinerary, move, stay);
    }

    public ItineraryDto createItinerary(
        ItineraryRequest itineraryRequest,
        Long tripId
    ) {
        // TODO trip이랑 합치면 날짜 비교해야함
//        var trip = tripService.findById(tripId);
//        if(!isValidDateTime(
//            trip.getStartDate(), trip.getEndDate(), itineraryRequest.getStartDatetime(), itineraryRequest.getEndDatetime()
//        )){
//            throw TravelError.TIME_ERROR.defaultException();
//        }

        ItineraryEntity itineraryEntity = ItineraryEntity.builder()
            .tripId(tripId)
            .itineraryName(itineraryRequest.getItineraryName())
            .type(itineraryRequest.getType())
            .startDatetime(itineraryRequest.getStartDatetime())
            .endDatetime(itineraryRequest.getEndDatetime())
            .itineraryComment(itineraryRequest.getItineraryComment())
            .build();
        try {
            itineraryRepository.save(itineraryEntity);
        }
        catch (Exception e){
            throw new ApiException(ErrorCode.BAD_REQUEST, e.getMessage());
        }


        MoveEntity moveEntity = null;
        StayEntity stayEntity = null;

        if(itineraryRequest.getType().getValue() == 0){
            moveEntity = MoveEntity.builder()
                .itineraryId(itineraryEntity.getItineraryId())
                .transportation(itineraryRequest.getTransportation())
                .departurePlace(itineraryRequest.getDeparturePlace())
                .arrivalPlace(itineraryRequest.getArrivalPlace())
                .departureLat(itineraryRequest.getDepartureLat())
                .departureLng(itineraryRequest.getDepartureLng())
                .arrivalLat(itineraryRequest.getArrivalLat())
                .arrivalLng(itineraryRequest.getArrivalLng())
                .build();
            moveRepository.save(moveEntity);
        }

        else{
            stayEntity = StayEntity.builder()
                .itineraryId(itineraryEntity.getItineraryId())
                .place(itineraryRequest.getPlace())
                .lat(itineraryRequest.getLat())
                .lng(itineraryRequest.getLng())
                .build();
            stayRepository.save(stayEntity);
        }

        return ItineraryDto.toDto(itineraryEntity, moveEntity, stayEntity);
    }

    @Transactional
    public ItineraryDto updateItinerary(
        Long tripId,
        Long itineraryId,
        ItineraryRequest itineraryRequest
    ) {

        //TODO trip 날짜비교
//        var trip = tripService.findById(tripId);
//        if(!isValidDateTime(
//            trip.getStartDate(), trip.getEndDate(), itineraryRequest.getStartDatetime(), itineraryRequest.getEndDatetime()
//        ))
//            throw TravelError.TIME_ERROR.defaultException();

        var itineraryEntity = itineraryRepository.findById(itineraryId)
            .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "Itinerary not found"));

        itineraryEntity.setTripId(tripId);
        itineraryEntity.setItineraryName(itineraryRequest.getItineraryName());
        itineraryEntity.setType(itineraryRequest.getType());
        itineraryEntity.setStartDatetime(itineraryRequest.getStartDatetime());
        itineraryEntity.setEndDatetime(itineraryRequest.getEndDatetime());
        itineraryEntity.setItineraryComment(itineraryRequest.getItineraryComment());

        itineraryRepository.save(itineraryEntity);

        moveRepository.deleteById(itineraryId);
        stayRepository.deleteById(itineraryId);

        MoveEntity moveEntity = null;
        StayEntity stayEntity = null;

        if(itineraryRequest.getType().getValue() == 0) {
            moveEntity = MoveEntity.builder()
                .itineraryId(itineraryId)
                .transportation(itineraryRequest.getTransportation())
                .departurePlace(itineraryRequest.getDeparturePlace())
                .arrivalPlace(itineraryRequest.getArrivalPlace())
                .departureLat(itineraryRequest.getDepartureLat())
                .departureLng(itineraryRequest.getDepartureLng())
                .arrivalLat(itineraryRequest.getArrivalLat())
                .arrivalLng(itineraryRequest.getArrivalLng())
                .build()
            ;
            moveRepository.save(moveEntity);

        } else {
            stayEntity = StayEntity.builder()
                .itineraryId(itineraryId)
                .place(itineraryRequest.getPlace())
                .lat(itineraryRequest.getLat())
                .lng(itineraryRequest.getLng())
                .build()
            ;
            stayRepository.save(stayEntity);
        }
        return ItineraryDto.toDto(itineraryEntity, moveEntity, stayEntity);
    }

    @Transactional
    public void deleteItinerary(Long tripId, Long itineraryId) {

        // TODO 연결한 후 tripId 확인
//        if (!tripRepository.existById(tripId)) {
//            throw new ApiException(ErrorCode.BAD_REQUEST, "Trip not found");
//        }

        if (!itineraryRepository.existsById(itineraryId)) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "Itinerary not found");
        } else {
            var type = itineraryRepository.findTypeByItineraryId(itineraryId);
            if (type.getValue() == 0) {
                moveRepository.deleteById(itineraryId);
            } else {
                stayRepository.deleteById(itineraryId);
            }
        }
    }

    public static boolean isValidDateTime(
        LocalDate startTravel, LocalDate endTravel,
        LocalDateTime startItinerary, LocalDateTime endItinerary
    ) {
        return (startItinerary.toLocalDate().isEqual(startTravel) || startItinerary.toLocalDate().isAfter(startTravel)) &&
            (endItinerary.toLocalDate().isEqual(endTravel) || endItinerary.toLocalDate().isBefore(endTravel)) &&
            (startItinerary.isBefore(endItinerary));
    }
}
