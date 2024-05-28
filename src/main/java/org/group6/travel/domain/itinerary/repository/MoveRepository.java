package org.group6.travel.domain.itinerary.repository;

import org.group6.travel.domain.itinerary.model.entity.MoveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoveRepository extends JpaRepository<MoveEntity, Long> {

    // select * from move where itineraryId = ? limit 1
    MoveEntity findFirstByItineraryId(Long itineraryId);
}
