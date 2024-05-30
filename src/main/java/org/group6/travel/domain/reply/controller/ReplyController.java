package org.group6.travel.domain.reply.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.group6.travel.common.api.ResponseApi;
import org.group6.travel.domain.reply.model.dto.ReplyDto;
import org.group6.travel.domain.reply.model.request.ReplyRequest;
import org.group6.travel.domain.reply.service.ReplyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/trip/{trip_id}/reply")
public class ReplyController {

    private final ReplyService replyService;

    //여행아이디 별 댓글 조회
    @GetMapping
    public ResponseApi<List<ReplyDto>> getReplies(@PathVariable("trip_id") Long tripId) {
        var reponse = replyService.getReplies(tripId);
        return ResponseApi.OK(reponse);
    }

    //여행 댓글 작성
    @PostMapping
    public ResponseApi<ReplyDto> createReply(
        @PathVariable("trip_id") Long tripId,
        @Valid @RequestBody ReplyRequest replyRequest
    ) {
        return ResponseApi.OK(replyService.createReply(tripId,replyRequest));
    }

    //댓글 수정
    @PutMapping("/{reply_id}")
    public ResponseApi<?> updateReply(
        @PathVariable("trip_id") Long tripId,
        @PathVariable("reply_id") Long replyId,
        @RequestBody ReplyRequest replyRequest
    ){
        var updateReply = replyService.updateReply(tripId, replyId, replyRequest);

        return ResponseApi.OK(updateReply);
    }

    //여행 댓글 삭제
    @DeleteMapping("/{reply_id}")
    public ResponseApi<String> deleteReply(
        @PathVariable("trip_id") Long tripId,
        @PathVariable("reply_id") Long replyId
    ) {
        replyService.deleteReply(tripId, replyId);
        return ResponseApi.OK("삭제 성공");

    }
}