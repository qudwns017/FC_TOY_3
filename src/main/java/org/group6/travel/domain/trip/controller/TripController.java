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

    @PostMapping
    public Api<?> insertTrip(
            @Valid
            @RequestBody TripRequest tripRequest
    ){
        return Api.OK(tripService.insertTrip(tripRequest));
    }

    @GetMapping
    public Api<List<?>> getTripList(){
        return Api.OK(tripService.getTripAll());
    }

    @GetMapping("/user")
    public Api<List<?>> getTripListByUser(){
        return Api.OK(tripService.getTripByUserId((long)1));
    }

    @GetMapping("/{tripId}")
    public Api<?> getTripId(
            @PathVariable Long tripId
    ){
        return Api.OK(tripService.getTripById(tripId));
    }

    @GetMapping("/user/like-list")
    public Api<List<?>> getTripListByUserLike(){
        return Api.OK(tripService.getTripByLike((long) 1));
    }

    @GetMapping("/search")
    public Api<List<?>> searchTripListByKeyword(
            @RequestParam("keyword") String keyword
    ){
        return Api.OK(tripService.getTripByKeyword(keyword));
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
