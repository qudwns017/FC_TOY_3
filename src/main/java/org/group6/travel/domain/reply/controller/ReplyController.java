package org.group6.travel.domain.reply.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.group6.travel.common.api.Api;
import org.group6.travel.domain.reply.model.dto.ReplyDto;
import org.group6.travel.domain.reply.model.request.ReplyRequest;
import org.group6.travel.domain.reply.service.ReplyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReplyController {

    private final ReplyService replyService;

    //여행 댓글 작성
    @PostMapping("/trip/{trip_id}/reply")
    public Api<?> create(
        @PathVariable("trip_id") Long tripId,
        @Valid @RequestBody ReplyRequest replyRequest
    ) {
        var replyDto = replyService.create(tripId, replyRequest);
        return Api.SUCCSESS(replyDto);
    }

    //여행아이디 별 댓글 조회
    @GetMapping("/trip/{trip_id}/reply")
    public Api<List<?>> getByTripId(@PathVariable("trip_id") Long tripId) {
        var id = replyService.getByTripId(tripId);
        return Api.OK(id);
    }

    //여행 댓글 삭제
    @DeleteMapping("/trip/{trip_id}/reply/{reply_id}")
    public Api<?> delete(
        @PathVariable("trip_id") Long tripId,
        @PathVariable("reply_id") Long replyId
    ) {
        replyService.delete(tripId, replyId);
        return Api.OK(delete(tripId, replyId));

    }

    //댓글 수정
    @PutMapping("/trip/{trip_id}/reply/{reply_id}")
    public Api<?> update(
        @PathVariable("trip_id") Long tripId,
        @PathVariable("reply_id") Long replyId,
        @RequestBody ReplyRequest replyRequest
    ){
        var updateReply = replyService.update(tripId, replyId, replyRequest);

        return Api.SUCCSESS(updateReply);
    }

}

/*
 //여행 댓글 수정
    @PutMapping("/trip/{trip_id}/reply/{reply_id}")
    public Api<?> update(
        @PathVariable("trip_id") Long tripId,
        @PathVariable("reply_id") Long replyId,
        @RequestBody ReplyRequest replyRequest
    ) {
        var updateReply = replyService.update(tripId, replyId,replyRequest);
        return Api.OK(updateReply);
    }

 */








