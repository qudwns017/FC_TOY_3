package org.group6.travel.domain.itinerary.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group6.travel.domain.itinerary.model.enums.ItineraryType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItineraryDto {

    private Long id;

    private Long tripId;

    private String name;

    private ItineraryType type;

    private LocalDateTime startDatetime;

    private LocalDateTime endDatetime;

    private String comment;

}
