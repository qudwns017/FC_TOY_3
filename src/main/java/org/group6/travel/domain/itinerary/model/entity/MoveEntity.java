package org.group6.travel.domain.itinerary.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "move")
public class MoveEntity {

    @Id
    @Column(nullable = false)
    private Long itineraryId;

    @Column(length = 50, nullable = false)
    private String transportation;

    @Column(length = 50, nullable = false)
    private String departurePlace;

    @Column(length = 50, nullable = false)
    private String arrivalPlace;

    @Column(nullable = false)
    private double departureLat;

    @Column(nullable = false)
    private double departureLng;

    @Column(nullable = false)
    private double arrivalLat;

    @Column(nullable = false)
    private double arrivalLng;
}
