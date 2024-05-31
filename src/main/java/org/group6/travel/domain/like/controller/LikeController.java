package org.group6.travel.domain.like.controller;

import lombok.RequiredArgsConstructor;
import org.group6.travel.common.api.ResponseApi;
import org.group6.travel.domain.like.service.LikeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    //좋아요 목록 조회
    @GetMapping("/user/like-list")
    public ResponseApi<List<?>> getlikes(){
        var likeList = likeService.getlikes();
        return ResponseApi.OK(likeList);
    }

    //여행 좋아요, post put 201
   @PostMapping("/trip/{trip_id}/like")
    public ResponseApi<?> addLike(
        @PathVariable("trip_id") Long tripId,
        @AuthenticationPrincipal User loginUser
    ) {
        var loginUserId = Long.parseLong(loginUser.getUsername());
        var clickLike = likeService.addLike(tripId, loginUserId);
        return ResponseApi.OK(clickLike);
    }

}