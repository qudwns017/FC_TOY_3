package org.group6.travel.domain.accommodation.repository;

import java.util.List;
import org.group6.travel.domain.accommodation.model.entity.AccommodationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<AccommodationEntity, Long> {
    List<AccommodationEntity> findByTripId(Long tripId);
}
