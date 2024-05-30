package org.group6.travel.domain.trip.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.group6.travel.common.api.ResponseApi;
import org.group6.travel.domain.token.model.entity.UserDetailsEntity;
import org.group6.travel.domain.trip.model.dto.TripDto;
import org.group6.travel.domain.trip.model.dto.TripRequest;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.service.TripService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip")
@Slf4j
public class TripController {
    private final TripService tripService;

    @GetMapping
    public ResponseApi<List<?>> getTrips(){
        return ResponseApi.OK(tripService.getTrips());
    }

    @GetMapping("/user")
    public ResponseApi<List<?>> getTripsByUser(){
        return ResponseApi.OK(tripService.getTripsByUser((long)1));
    }

    @GetMapping("/{tripId}")
    public ResponseApi<?> getTripId(
            @PathVariable Long tripId
    ){
        return ResponseApi.OK(tripService.getTripById(tripId));
    }

    @GetMapping("/user/like-list")
    public ResponseApi<List<?>> getTripsByUserLike(){
        return ResponseApi.OK(tripService.getTripsByUserLike((long) 1));
    }

    @GetMapping("/search")
    public ResponseApi<List<?>> getTripsByKeyword(
            @RequestParam("keyword") String keyword
    ){
        return ResponseApi.OK(tripService.getTripsByKeyword(keyword));
    }

    @PostMapping
    public ResponseApi<?> createTrip(
            @AuthenticationPrincipal
            UserDetailsEntity userDetails,
        @Valid
        @RequestBody TripRequest tripRequest
    ){
        log.info(userDetails.getUserId().toString());
        return ResponseApi.OK(tripService.createTrip(tripRequest));
    }

    @PutMapping("/{tripId}")
    public ResponseApi<?> updateTrip(
            @PathVariable Long tripId,
            @Valid
            @RequestBody TripRequest tripRequest
    ){
        return ResponseApi.OK(tripService.updateTrip(tripId, tripRequest));
    }

    @DeleteMapping("/{tripId}")
    public ResponseApi<?> deleteTrip(
            @PathVariable Long tripId
    ){
        tripService.deleteTrip(tripId);
        return ResponseApi.OK("삭제 성공");
    }
}
