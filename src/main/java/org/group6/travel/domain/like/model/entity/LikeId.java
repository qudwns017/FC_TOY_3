package org.group6.travel.domain.like.model.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class LikeId implements Serializable {

    private Long userId; //UserEntity user;

    private Long tripId; //tripEntity trip;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeId likeId = (LikeId) o;
        return Objects.equals(userId, likeId.userId) &&
            Objects.equals(tripId, likeId.tripId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, tripId);
    }
}
