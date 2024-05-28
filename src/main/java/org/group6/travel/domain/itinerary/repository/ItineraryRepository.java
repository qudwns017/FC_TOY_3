package org.group6.travel.domain.itinerary.repository;

import org.group6.travel.domain.itinerary.model.entity.ItineraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItineraryRepository extends JpaRepository<ItineraryEntity, Long> {

}
