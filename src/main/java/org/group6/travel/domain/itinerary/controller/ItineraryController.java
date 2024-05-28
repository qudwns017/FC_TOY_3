package org.group6.travel.domain.itinerary.controller;

import lombok.RequiredArgsConstructor;
import org.group6.travel.domain.itinerary.service.ItineraryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/trips/{tripId}/itinerary")
public class ItineraryController {

    private final ItineraryService itineraryService;

    @GetMapping("")
    public Api<List<>>
}
