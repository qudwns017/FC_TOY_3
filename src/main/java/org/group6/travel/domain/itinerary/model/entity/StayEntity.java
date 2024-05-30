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

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@DiscriminatorValue("1")
@Table(name="stay")
public class StayEntity extends ItineraryEntity{

    @Column(length = 50, nullable = false)
    private String place;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

}
