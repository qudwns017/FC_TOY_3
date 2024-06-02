package org.group6.travel.domain.trip.repository;

import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public interface TripRepository extends JpaRepository<TripEntity,Long> {

    Optional<List<TripEntity>> findByUserId(Long userId);

    List<TripEntity> findByTripNameContains(String keyword);

    @Query(value = "select * from trip trip where trip.id in ?1", nativeQuery = true)
    List<TripEntity> findByLikeList(List<Long> likeList);

    Optional<TripEntity> findByTripId(Long tripId);

    @Transactional
    @Modifying
    @Query("UPDATE TripEntity t SET t.likeCount = t.likeCount + 1 WHERE t.tripId = :tripId")
    void incrementLikeCount(Long tripId);

    @Transactional
    @Modifying
    @Query("UPDATE TripEntity t SET t.likeCount = t.likeCount - 1 WHERE t.tripId = :tripId")
    void decrementLikeCount(Long tripId);
}
