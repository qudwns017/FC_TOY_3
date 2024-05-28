package org.group6.travel.domain.trip.repository;

import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, Long> {
}
