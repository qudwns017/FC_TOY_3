package org.group6.travel.domain.accommodation.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.group6.travel.domain.accommodation.service.AccommodationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/trips/{tripId}/accommodation")
@Tag(name = "accommodation", description = "숙박")
public class AccommodationController {

  private final AccommodationService accommodationService;

}
