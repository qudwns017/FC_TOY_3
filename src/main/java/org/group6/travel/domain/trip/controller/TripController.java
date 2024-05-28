package org.group6.travel.domain.trip.controller;

import lombok.RequiredArgsConstructor;
import org.group6.travel.common.api.Api;
import org.group6.travel.domain.trip.model.dto.TripDto;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.service.TripService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip")
public class TripController {
    private final TripService tripService;

    @GetMapping
    public Api<List<TripEntity>> getTripList(){
        return Api.OK(tripService.getTripAll());
    }
}
