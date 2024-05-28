package org.group6.travel.domain.accommodation.model.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accommodation")
public class AccommodationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @ManyToOne
    private Long tripId;

    @Column(length = 50, nullable = false)
    private String name;

    // latitude
    @Column(nullable = false)
    private Double lat;
    // longitude
    @Column(nullable = false)
    private Double lng;

    @Column(nullable = false)
    private LocalDateTime checkInDatetime;

    @Column(nullable = false)
    private LocalDateTime checkOutDatetime;
}
