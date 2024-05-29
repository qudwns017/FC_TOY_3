package org.group6.travel.domain.itinerary.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group6.travel.domain.itinerary.model.entity.ItineraryEntity;
import org.group6.travel.domain.itinerary.model.entity.MoveEntity;
import org.group6.travel.domain.itinerary.model.entity.StayEntity;
import org.group6.travel.domain.itinerary.model.enums.ItineraryType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItineraryDto {

    private Long itineraryId;

    private Long tripId;

    private String itineraryName;

    private ItineraryType type;

    private LocalDateTime startDatetime;

    private LocalDateTime endDatetime;

    private String itineraryComment;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String transportation;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String departurePlace;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String arrivalPlace;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double departureLat;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double departureLng;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double arrivalLat;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double arrivalLng;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String place;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double lat;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double lng;

    public static ItineraryDto toDto(
        MoveEntity move,
        StayEntity stay
    ) {
        return ItineraryDto.builder()
            .itineraryId(move != null ? move.getItineraryId() : stay.getItineraryId())
            .tripId(move != null ? move.getTripEntity().getTripId() : stay.getTripEntity().getTripId())
            .itineraryName(move != null ? move.getItineraryName(): stay.getItineraryName())
            .type(move != null ? move.getType(): stay.getType())
            .startDatetime(move != null ? move.getStartDatetime(): stay.getStartDatetime())
            .endDatetime(move != null ? move.getEndDatetime(): stay.getEndDatetime())
            .itineraryComment(move != null ? move.getItineraryComment(): stay.getItineraryComment())
            .transportation(move != null ? move.getTransportation() : null)
            .departurePlace(move != null ? move.getDeparturePlace() : null)
            .arrivalPlace(move != null ? move.getArrivalPlace() : null)
            .departureLat(move != null ? move.getDepartureLat() : null)
            .departureLng(move != null ? move.getDepartureLng() : null)
            .arrivalLat(move != null ? move.getArrivalLat() : null)
            .arrivalLng(move != null ? move.getArrivalLng() : null)
            .place(stay != null ? stay.getPlace() : null)
            .lat(stay != null ? stay.getLat() : null)
            .lng(stay != null ? stay.getLng() : null)
            .build();
    }
}
