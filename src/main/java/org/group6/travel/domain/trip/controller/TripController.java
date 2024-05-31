package org.group6.travel.domain.trip.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.group6.travel.common.api.ResponseApi;
import org.group6.travel.domain.trip.model.dto.TripDto;
import org.group6.travel.domain.trip.model.dto.TripRequest;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.service.TripService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip")
public class TripController {
    private final TripService tripService;

    @GetMapping
    public ResponseApi<List<?>> getTrips(){
        return ResponseApi.OK(tripService.getTrips());
    }

    @GetMapping("/user")
    public ResponseApi<List<?>> getTripsByUser(
        @AuthenticationPrincipal User loginUser
    ){
        var loginUserID = Long.parseLong(loginUser.getUsername());
        return ResponseApi.OK(tripService.getTripsByUser(loginUserID));
    }

    @GetMapping("/{tripId}")
    public ResponseApi<?> getTripId(
            @PathVariable Long tripId
    ){
        return ResponseApi.OK(tripService.getTripById(tripId));
    }

    @GetMapping("/user/like-list")
    public ResponseApi<List<?>> getTripsByUserLike(
        @AuthenticationPrincipal User loginUser
    ){
        var loginUserID = Long.parseLong(loginUser.getUsername());
        return ResponseApi.OK(tripService.getTripsByUserLike(loginUserID));
    }

    @GetMapping("/search")
    public ResponseApi<List<?>> getTripsByKeyword(
            @RequestParam("keyword") String keyword
    ){
        return ResponseApi.OK(tripService.getTripsByKeyword(keyword));
    }

    @PostMapping
    public ResponseApi<?> createTrip(
        @Valid
        @RequestBody TripRequest tripRequest,
        @AuthenticationPrincipal User loginUser
    ){
        var loginUserID = Long.parseLong(loginUser.getUsername());
        return ResponseApi.OK(tripService.createTrip(tripRequest, loginUserID));
    }

    @PutMapping("/{tripId}")
    public ResponseApi<?> updateTrip(
        @PathVariable Long tripId,
        @Valid
        @RequestBody TripRequest tripRequest,
        @AuthenticationPrincipal User loginUser

    ){
        var loginUserID = Long.parseLong(loginUser.getUsername());
        return ResponseApi.OK(tripService.updateTrip(tripId, tripRequest, loginUserID));
    }

    @DeleteMapping("/{tripId}")
    public ResponseApi<?> deleteTrip(
        @PathVariable Long tripId,
        @AuthenticationPrincipal User loginUser
    ){
        var loginUserID = Long.parseLong(loginUser.getUsername());
        tripService.deleteTrip(tripId, loginUserID);
        return ResponseApi.OK("삭제 성공");
    }
}
