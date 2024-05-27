package org.group6.travel.domain.reply.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.group6.travel.common.api.Api;
import org.group6.travel.domain.reply.model.dto.ReplyDto;
import org.group6.travel.domain.reply.model.entity.ReplyEntity;
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

}
/*



    public Api<ReplyDto> create(
        @PathVariable("trip_id") Long tripId,
        @Valid @RequestBody ReplyRequest replyRequest
    ) {
        var replyDto = replyService.create(tripId, replyRequest);
        return Api.OK(replyDto);
    }


  //여행 댓글 조회
    @GetMapping("/trip/{trip_id}/reply")
    Api<List<ReplyDto>>getByTripId(@PathVariable Long tripId){

    }

    //여행 댓글 수정
    @PutMapping("/trip/{trip_id}/reply/{reply_id}")


    //여행 댓글 삭제
    @DeleteMapping("/trip/{trip_id}/reply/{reply_id}")

 */