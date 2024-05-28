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
@Table(name = "stay")
public class StayEntity {

    @Id
    @Column(nullable = false)
    private Long itineraryId;

    @Column(length = 50, nullable = false)
    private String place;

    @Column(nullable = false)
    private double lat;

    @Column(nullable = false)
    private double lng;

}
