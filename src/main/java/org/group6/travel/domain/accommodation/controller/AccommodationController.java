package org.group6.travel.domain.accommodation.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.group6.travel.common.api.ResponseApi;
import org.group6.travel.domain.accommodation.model.dto.AccommodationDto;
import org.group6.travel.domain.accommodation.model.dto.AccommodationRequest;
import org.group6.travel.domain.accommodation.service.AccommodationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/trip/{tripId}/accommodation")
@Tag(name = "accommodation", description = "숙박")
public class AccommodationController {

    private final AccommodationService accommodationService;

    @GetMapping
    public ResponseApi<List<AccommodationDto>> getAccommodationList(
        @AuthenticationPrincipal User loginUser,
        @PathVariable Long tripId
    ) {
        var loginUserID = Long.parseLong(loginUser.getUsername());
      var response = accommodationService.getAccommodationList(tripId, loginUserID);
      return ResponseApi.OK(response);
    }

    @PostMapping
    public ResponseApi<AccommodationDto> createAccommodation(
        @PathVariable Long tripId,
        @Valid @RequestBody AccommodationRequest accommodationRequest
    ){
      var response = accommodationService.createAccommodation(tripId, accommodationRequest);
      return ResponseApi.OK(response);
    }

    @DeleteMapping("/{accommodationId}")
    public ResponseApi<Object> deleteAccommodation(
        @PathVariable Long tripId,
        @PathVariable Long accommodationId
    ) {
        accommodationService.deleteAccommodation(tripId, accommodationId);
        return ResponseApi.OK("Deleted accommodation with id " + accommodationId);
    }

}
