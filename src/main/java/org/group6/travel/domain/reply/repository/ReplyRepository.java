package org.group6.travel.domain.reply.repository;

import org.group6.travel.domain.reply.model.entity.ReplyEntity;
import org.group6.travel.domain.reply.model.request.ReplyRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

    List<ReplyEntity> findAllByTripId(Long tripId);

    Optional deleteByReplyIdAndTripId(Long replyId, Long tripId);

    @Modifying
    @Query("UPDATE ReplyEntity r SET r.replyComment = :comment WHERE r.replyId = :id AND r.tripId = :trip_id")
    void updateCommentByIdAndTripId(@Param("comment") String comment, @Param("id") Long id, @Param("trip_id") Long tripId);

    ReplyEntity findByTripId(Long tripId);
    ReplyEntity findByReplyId(Long replyId);

    @Modifying
    @Query("UPDATE ReplyEntity r SET r.replyComment = :#{#replyEntity.replyComment} WHERE r.replyId = :#{#replyEntity.replyId}")
    void updateReply(@Param("replyEntity") ReplyEntity replyEntity);

    ReplyEntity findByTripIdAndReplyId(Long tripId, Long replyId);


}