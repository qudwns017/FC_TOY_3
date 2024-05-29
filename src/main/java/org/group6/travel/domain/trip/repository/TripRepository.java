package org.group6.travel.domain.trip.repository;

import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<TripEntity,Long> {

    Optional<List<TripEntity>> findByUserId(Long userId);
    List<TripEntity> findByTripNameContains(String keyword);
    @Query(value = "select * from trip trip where trip.id in ?1", nativeQuery = true)
    public List<TripEntity> findByLikeList(List<Long> likeList);
    TripEntity findByTripId(Long tripId);
}
