package org.group6.travel.domain.trip.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.group6.travel.common.api.Api;
import org.group6.travel.domain.trip.model.dto.TripDto;
import org.group6.travel.domain.trip.model.dto.TripRequest;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.service.TripService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip")
public class TripController {
    private final TripService tripService;

    @GetMapping
    public Api<List<?>> getTrips(){
        return Api.OK(tripService.getTrips());
    }

    @GetMapping("/user")
    public Api<List<?>> getTripsByUser(){
        return Api.OK(tripService.getTripsByUser((long)1));
    }

    @GetMapping("/{tripId}")
    public Api<?> getTripId(
            @PathVariable Long tripId
    ){
        return Api.OK(tripService.getTripById(tripId));
    }

    @GetMapping("/user/like-list")
    public Api<List<?>> getTripsByUserLike(){
        return Api.OK(tripService.getTripsByUserLike((long) 1));
    }

    @GetMapping("/search")
    public Api<List<?>> getTripsByKeyword(
            @RequestParam("keyword") String keyword
    ){
        return Api.OK(tripService.getTripsByKeyword(keyword));
    }

    @PostMapping
    public Api<?> createTrip(
        @Valid
        @RequestBody TripRequest tripRequest
    ){
        return Api.OK(tripService.createTrip(tripRequest));
    }

    @PutMapping("/{tripId}")
    public Api<?> updateTrip(
            @PathVariable Long tripId,
            @Valid
            @RequestBody TripRequest tripRequest
    ){
        return Api.OK(tripService.updateTrip(tripId, tripRequest));
    }

    @DeleteMapping("/{tripId}")
    public Api<?> deleteTrip(
            @PathVariable Long tripId
    ){
        tripService.deleteTrip(tripId);
        return Api.OK("삭제 성공");
    }
}
