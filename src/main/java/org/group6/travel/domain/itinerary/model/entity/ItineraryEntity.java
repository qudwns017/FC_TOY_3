package org.group6.travel.domain.itinerary.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.group6.travel.domain.itinerary.model.enums.ItineraryType;
import org.group6.travel.domain.trip.model.entity.TripEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER, columnDefinition = "bit")
@Table(name = "itinerary")
public class ItineraryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long itineraryId;

    @ManyToOne
    @JoinColumn(name="trip_id")
    @JsonIgnore
    @ToString.Exclude
    private TripEntity tripEntity;

    @Column(name = "name", length =50, nullable = false)
    private String itineraryName;

    @Column(nullable = false, columnDefinition = "bit", insertable = false, updatable = false)
    @Enumerated(EnumType.ORDINAL)
    private ItineraryType type;

    @Column(nullable = false)
    private LocalDateTime startDatetime;

    @Column(nullable = false)
    private LocalDateTime endDatetime;

    @Column(name = "comment")
    private String itineraryComment;
}
