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

    //좋아요 목록 조회
    @GetMapping("/user/like-list")
    public Api<List<?>> getlikes(){
        var likeList = likeService.getlikes();
        return Api.OK(likeList);
    }

    //여행 좋아요, post put 201
   @PostMapping("/trip/{trip_id}/like")
    public Api<?> addLike(
        @PathVariable("trip_id") Long tripId
    ) {
        var clickLike = likeService.addLike(tripId);
        return Api.SUCCESS(clickLike);
    }

}