package org.group6.travel.domain.like.controller;

import lombok.RequiredArgsConstructor;
import org.group6.travel.common.api.Api;
import org.group6.travel.domain.like.service.LikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    //여행 좋아요, post put 201
   @PostMapping("/trip/{trip_id}/like")
    public Api<?> like(
        @PathVariable("trip_id") Long tripId,
        @PathVariable("user_id") Long userId
    ) {
        var clickLike = likeService.like(tripId,userId);
        return Api.SUCCSESS(clickLike);
    }

    //좋아요 목록 조회
    @GetMapping("/user/like-list")
    public Api<List<?>> LikeList(){
        var likeList = likeService.all();
        return Api.OK(likeList);
    }


}