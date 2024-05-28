package org.group6.travel.domain.accommodation.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccommodationDto {
  private Long id;

  //  @ManyToOne
  private Long tripId;
  private String name;

  //  latitude
  private Double lat;
  // longitude
  private Double lng;

  private LocalDateTime checkInDatetime;
  private LocalDateTime checkOutDatetime;
}
