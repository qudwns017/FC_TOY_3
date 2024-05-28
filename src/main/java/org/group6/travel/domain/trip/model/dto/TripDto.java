package org.group6.travel.domain.trip.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private String comment;
}
