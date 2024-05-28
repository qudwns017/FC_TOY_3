package org.group6.travel.domain.reply.repository;

import org.group6.travel.domain.reply.model.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

    List<ReplyEntity> findAllByTripId(Long tripId);
}
