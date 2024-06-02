
package org.group6.travel.domain.like.repository;

import org.group6.travel.domain.like.model.entity.LikeEntity;
import org.group6.travel.domain.like.model.entity.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, LikeId> {


    LikeEntity findByUserIdAndTripId(Long userId, Long tripId); //UserEntity TripEntity

    @Query(value="SELECT trip_id from likes likes where likes.user_id=?1", nativeQuery = true)
    List<Long> findByUserId(Long userId);

    Optional<LikeEntity> findByTripId(Long tripId);
    //LikeEntity deleteByLikeId(Long LikeId); 취소시 id 필요?


}

