package org.group6.travel.domain.itinerary.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.group6.travel.domain.itinerary.model.enums.ItineraryType;

import static org.group6.travel.domain.itinerary.model.enums.ItineraryType.MOVE;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@DiscriminatorValue("0")
@Table(name = "move")
public class MoveEntity extends ItineraryEntity{

    @Column(length = 50, nullable = false)
    private String transportation;

    @Column(length = 50, nullable = false)
    private String departurePlace;

    @Column(length = 50, nullable = false)
    private String arrivalPlace;

    @Column(nullable = false)
    private Double departureLat;

    @Column(nullable = false)
    private Double departureLng;

    @Column(nullable = false)
    private Double arrivalLat;

    @Column(nullable = false)
    private Double arrivalLng;
}
