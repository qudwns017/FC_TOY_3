package org.group6.travel.domain.trip.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group6.travel.domain.trip.model.enums.DomesticType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "trip")
public class TripEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long tripId;
    private Long userId;
    @Column(name="name")
    private String tripName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean domestic;
    @Column(name="comment")
    private String tripComment;
    private int likeCount;
}
