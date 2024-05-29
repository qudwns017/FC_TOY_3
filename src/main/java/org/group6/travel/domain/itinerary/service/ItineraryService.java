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
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.repository.TripRepository;
import org.group6.travel.domain.trip.service.TripService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final MoveRepository moveRepository;
    private final StayRepository stayRepository;
    private final TripService tripService;
    private final TripRepository tripRepository;

    public List<ItineraryDto> getItinerary(Long tripId) {
//        if (tripService.getTripId(tripId) == null) {
//            throw new ApiException(ErrorCode.NULL_POINT, "없는 여행 아이디입니다.");
//        }
        var tripEntity = tripService.getTripById(tripId);
        List<ItineraryEntity> itineraries = itineraryRepository.findAllByTripEntity(tripEntity);

        return itineraries.stream()
            .map(this::mapToItineraryDto)
            .collect(Collectors.toList());
    }

    private ItineraryDto mapToItineraryDto(ItineraryEntity itinerary) {
        MoveEntity move = itinerary.getType().equals(ItineraryType.MOVE) ? moveRepository.findFirstByItineraryId(itinerary.getItineraryId()) : null;
        StayEntity stay = itinerary.getType().equals(ItineraryType.STAY) ? stayRepository.findFirstByItineraryId(itinerary.getItineraryId()) : null;

        return ItineraryDto.toDto(move, stay);
    }

    @Transactional
    public ItineraryDto createItinerary(
        ItineraryRequest itineraryRequest,
        Long tripId
    ) {
        var tripEntity = tripService.getTripById(tripId);

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
                    .departureLat(itineraryRequest.getDepartureLat())
                    .departureLng(itineraryRequest.getDepartureLng())
                    .arrivalLat(itineraryRequest.getArrivalLat())
                    .arrivalLng(itineraryRequest.getArrivalLng())
                    .build();
            moveRepository.save(moveEntity);
        }
        else{
            stayEntity = StayEntity.builder()
                    .itineraryId(itineraryId)
                    .tripEntity(tripEntity)
                    .itineraryName(itineraryRequest.getItineraryName())
                    .type(itineraryRequest.getType())
                    .startDatetime(itineraryRequest.getStartDatetime())
                    .endDatetime(itineraryRequest.getEndDatetime())
                    .itineraryComment(itineraryRequest.getItineraryComment())
                    .place(itineraryRequest.getPlace())
                    .lat(itineraryRequest.getLat())
                    .lng(itineraryRequest.getLng())
                    .build();
            stayRepository.save(stayEntity);
        }
        return ItineraryDto.toDto(moveEntity, stayEntity);
    }
    @Transactional
    public ItineraryDto updateItinerary(
            Long tripId,
            Long itineraryId,
            ItineraryRequest itineraryRequest
    ){
        var tripEntity = tripService.getTripById(tripId);

        if(!isValidDateTime(
            tripEntity.getStartDate(), tripEntity.getEndDate(), itineraryRequest.getStartDatetime(), itineraryRequest.getEndDatetime()
        )){
            throw new ApiException(ErrorCode.TIME_ERROR, "여행 시간 범위에 들어가지 않습니다.");
        }

        var itineraryEntity = itineraryRepository.findById(itineraryId)
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST, "Itinerary not found"));

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
    public void deleteItinerary(Long tripId, Long itineraryId) {

        // TODO 연결한 후 tripId 확인
//        if (!tripRepository.existById(tripId)) {
//            throw new ApiException(ErrorCode.BAD_REQUEST, "Trip not found");
//        }

        if (!itineraryRepository.existsById(itineraryId)) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "Itinerary not found");
        } else {
            itineraryRepository.deleteById(itineraryId);
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
