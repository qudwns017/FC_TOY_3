package org.group6.travel.domain.trip.repository;

import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<TripEntity,Long> {

    Optional<List<TripEntity>> findByTripNameContains(String keyword);
}
