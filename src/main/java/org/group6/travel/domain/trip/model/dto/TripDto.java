package org.group6.travel.domain.trip.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.model.enums.DomesticType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripDto {
    private Long tripId;
    private Long userId;
    private String tripName;
    private LocalDate startDate;
    private LocalDate endDate;
    private DomesticType domestic;
    private Integer likeCount;
    private String tripComment;

    public static TripDto toDto(TripEntity trip){
        return TripDto.builder()
                .tripId(trip.getTripId())
                .userId(trip.getUserId())
                .tripName(trip.getTripName())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .domestic(trip.getDomestic())
                .likeCount(trip.getLikeCount())
                .tripComment(trip.getTripComment())
                .build();
    }
}
