package org.group6.travel.domain.itinerary.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.*;
import org.group6.travel.domain.itinerary.model.enums.ItineraryType;
import org.group6.travel.domain.trip.model.entity.TripEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "itinerary")
public class ItineraryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="trip_id")
    @JsonIgnore
    @ToString.Exclude
    private TripEntity tripEntity;

    @Column(length =50, nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean type;

    @Column(nullable = false)
    private LocalDateTime startDatetime;

    @Column(nullable = false)
    private LocalDateTime endDatetime;

    private String comment;
}
