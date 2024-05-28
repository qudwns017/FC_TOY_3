package org.group6.travel.domain.accommodation.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.*;
import org.group6.travel.domain.trip.model.entity.TripEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "accommodation")
public class AccommodationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

//  @ManyToOne
  @ManyToOne
  @JoinColumn(name="trip_id")
  @JsonIgnore
  @ToString.Exclude
  private TripEntity tripEntity;

  private String name;

  private Double lat;
  private Double lng;

  private LocalDateTime checkInDatetime;
  private LocalDateTime checkOutDatetime;
}
