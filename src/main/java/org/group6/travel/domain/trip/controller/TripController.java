package org.group6.travel.domain.trip.controller;

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
    public Api<TripEntity> insertTrip(
            @RequestBody TripRequest tripRequest
    ){
        return Api.OK(tripService.insertTrip(tripRequest));
    }

    @GetMapping
    public Api<List<TripEntity>> getTripList(){
        return Api.OK(tripService.getTripAll());
    }

    @GetMapping("/{tripId}")
    public Api<TripEntity> getTripId(
            @PathVariable Long tripId
    ){
        return Api.OK(tripService.getTripById(tripId));
    }

    @GetMapping("/search")
    public Api<List<TripEntity>> searchTripListByKeyword(
            @RequestParam("keyword") String keyword
    ){
        return Api.OK(tripService.getTripByKeyword(keyword));
    }

    @PutMapping("/{tripId}")
    public Api<TripEntity> updateTrip(
            @PathVariable Long tripId,
            @RequestBody TripRequest tripRequest
    ){
        return Api.OK(tripService.updateTrip(tripId, tripRequest));
    }

    @DeleteMapping("/{tripId}")
    public Api<String> deleteTrip(
            @PathVariable Long tripId
    ){
        tripService.deleteTrip(tripId);
        return Api.OK("삭제 성공");
    }
}
