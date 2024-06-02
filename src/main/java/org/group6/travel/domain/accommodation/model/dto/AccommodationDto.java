package org.group6.travel.domain.accommodation.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group6.travel.domain.accommodation.model.entity.AccommodationEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccommodationDto {
    private Long id;

    private Long tripId;
    private String name;

    private Double latitude;
    private Double longitude;

    private LocalDateTime checkInDatetime;
    private LocalDateTime checkOutDatetime;

    public static AccommodationDto toAccommodationDto(AccommodationEntity accommodationEntity) {
        return AccommodationDto.builder()
            .tripId(accommodationEntity.getTripEntity().getTripId())
            .id(accommodationEntity.getId())
            .name(accommodationEntity.getName())
            .latitude(accommodationEntity.getLatitude())
            .longitude(accommodationEntity.getLongitude())
            .checkInDatetime(accommodationEntity.getCheckInDatetime())
            .checkOutDatetime(accommodationEntity.getCheckOutDatetime())
            .build();
    }
}
