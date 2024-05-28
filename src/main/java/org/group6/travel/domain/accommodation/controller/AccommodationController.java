package org.group6.travel.domain.accommodation.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.group6.travel.domain.accommodation.model.dto.AccommodationDto;
import org.group6.travel.domain.accommodation.model.dto.AccommodationRequest;
import org.group6.travel.domain.accommodation.service.AccommodationService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/trips/{tripId}/accommodation")
@Tag(name = "accommodation", description = "숙박")
public class AccommodationController {

    private final AccommodationService accommodationService;

    @GetMapping("")
    public List<AccommodationDto> findByTripId(
        @PathVariable Long tripId
    ) {
      return accommodationService.findByTripId(tripId);
    }

    @PostMapping("")
    public AccommodationDto create(
        @PathVariable Long tripId,
        @Valid @RequestBody AccommodationRequest accommodationRequest
    ){
      return accommodationService.save(tripId, accommodationRequest);
    }

    @DeleteMapping("/{accommodationId}")
    public String delete(
        @PathVariable Long tripId,
        @PathVariable Long accommodationId
    ) {
        accommodationService.delete(tripId, accommodationId);
        return "Success";
    }

}
