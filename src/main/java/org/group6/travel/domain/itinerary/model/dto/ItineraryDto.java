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
    private Double departureLatitude;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double departureLongitude;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double arrivalLatitude;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double arrivalLongitude;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String place;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double latitude;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double longitude;

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
            .departureLatitude(move != null ? move.getDepartureLatitude() : null)
            .departureLongitude(move != null ? move.getDepartureLongitude() : null)
            .arrivalLatitude(move != null ? move.getArrivalLatitude() : null)
            .arrivalLongitude(move != null ? move.getArrivalLongitude() : null)
            .place(stay != null ? stay.getPlace() : null)
            .latitude(stay != null ? stay.getLatitude() : null)
            .longitude(stay != null ? stay.getLongitude() : null)
            .build();
    }
}
