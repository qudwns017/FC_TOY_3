package org.group6.travel.domain.like.model.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "likes")
@IdClass(LikeId.class)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LikeEntity {


    @Id
    @Column(name = "user_id")
    //@ManyToOne(fetch = FetchType.LAZY)
    private Long userId; //private UserEntity user


    @Id
    @Column(name = "trip_id")
    //@ManyToOne(fetch = FetchType.LAZY)
    private Long tripId; //private TripEntity trip


}
