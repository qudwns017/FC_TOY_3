package org.group6.travel.domain.reply.repository;

import org.group6.travel.domain.reply.model.entity.ReplyEntity;
import org.group6.travel.domain.reply.model.request.ReplyRequest;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

    List<ReplyEntity> findAllByTripEntity(TripEntity trip);

    Optional<ReplyEntity> deleteByReplyIdAndTripEntity(Long replyId, TripEntity trip);

    Optional<ReplyEntity> findByReplyId(Long replyId);

}