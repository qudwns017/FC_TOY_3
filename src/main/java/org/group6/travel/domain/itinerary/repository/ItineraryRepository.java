package org.group6.travel.domain.itinerary.repository;

import java.util.List;
import org.group6.travel.domain.itinerary.model.entity.ItineraryEntity;
import org.group6.travel.domain.itinerary.model.entity.MoveEntity;
import org.group6.travel.domain.itinerary.model.entity.StayEntity;
import org.group6.travel.domain.itinerary.model.enums.ItineraryType;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItineraryRepository extends JpaRepository<ItineraryEntity, Long> {

    // select * from itinerary where tripId = ?
    List<ItineraryEntity> findAllByTripEntity(TripEntity tripEntity);

    @Query("SELECT i.type FROM ItineraryEntity i WHERE i.itineraryId = :itineraryId")
    ItineraryType findTypeByItineraryId(@Param("itineraryId") Long itineraryId);
}
