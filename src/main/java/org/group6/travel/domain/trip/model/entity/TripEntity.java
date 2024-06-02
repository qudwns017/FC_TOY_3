package org.group6.travel.domain.trip.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group6.travel.domain.accommodation.model.entity.AccommodationEntity;
import org.group6.travel.domain.itinerary.model.entity.ItineraryEntity;
import org.group6.travel.domain.reply.model.entity.ReplyEntity;
import org.group6.travel.domain.trip.model.enums.DomesticType;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(columnDefinition = "bit")
    @Enumerated(EnumType.ORDINAL)
    private DomesticType domestic;
    @Column(name="comment")
    private String tripComment;
    private int likeCount;

    @OneToMany(
            mappedBy = "tripEntity"
    )
    private List<ItineraryEntity> itineraryList = List.of();

    @OneToMany(
            mappedBy = "tripEntity"
    )
    private List<AccommodationEntity> accommodationList = List.of();
    @OneToMany(
            mappedBy = "tripEntity"
    )
    private List<ReplyEntity> replyList = List.of();
}