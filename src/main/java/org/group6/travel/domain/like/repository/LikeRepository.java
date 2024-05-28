
package org.group6.travel.domain.like.repository;

import org.group6.travel.domain.like.model.entity.LikeEntity;
import org.group6.travel.domain.like.model.entity.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, LikeId> {


    LikeEntity findByUserIdAndTripId(Long userId, Long tripId); //UserEntity TripEntity

    LikeEntity findByTripId(Long tripId);
    //LikeEntity deleteByLikeId(Long LikeId); 취소시 id 필요?


}

