package org.group6.travel.domain.itinerary.repository;

import org.group6.travel.domain.itinerary.model.entity.MoveEntity;
import org.group6.travel.domain.itinerary.model.entity.StayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StayRepository extends JpaRepository<StayEntity, Long> {

    // select * from stay where itineraryId = ? limit 1
    StayEntity findFirstByItineraryId(Long itineraryId);
}
