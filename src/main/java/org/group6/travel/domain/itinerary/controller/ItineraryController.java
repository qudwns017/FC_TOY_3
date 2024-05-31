package org.group6.travel.domain.itinerary.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.group6.travel.common.api.ResponseApi;
import org.group6.travel.domain.itinerary.model.dto.ItineraryDto;
import org.group6.travel.domain.itinerary.model.dto.ItineraryRequest;
import org.group6.travel.domain.itinerary.service.ItineraryService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/trip/{tripId}/itinerary")
public class ItineraryController {

    private final ItineraryService itineraryService;

    @GetMapping
    public ResponseApi<List<ItineraryDto>> getItineraries(
        @PathVariable
        Long tripId
    ) {
        var response = itineraryService.getItinerary(tripId);
        return ResponseApi.OK(response);
    }

    @PostMapping
    public ResponseApi<ItineraryDto> createItinerary(
        @Parameter(name ="tripId", description = "TripId 입력", required = true, in = ParameterIn.PATH)
        @PathVariable
        Long tripId,
        @AuthenticationPrincipal User loginUser,
        @Valid
        @RequestBody
        ItineraryRequest itineraryRequest
    ) {
        var loginUserId = Long.parseLong(loginUser.getUsername());
        var response = itineraryService.createItinerary(itineraryRequest, tripId, loginUserId);
        return ResponseApi.OK(response);
    }

    @PutMapping("/{itineraryId}")
    public ResponseApi<ItineraryDto> updateItinerary(
        @Parameter(name ="tripId", description = "TripId 입력", required = true, in = ParameterIn.PATH)
        @PathVariable
        Long tripId,
        @Parameter(name ="itineraryId", description = "Itinerary 입력", required = true, in = ParameterIn.PATH)
        @PathVariable
        Long itineraryId,
        @AuthenticationPrincipal User loginUser,
        @Parameter(name ="itineraryRequest", description = "여정 정보 입력", required = true)
        @Validated @RequestBody
        ItineraryRequest itineraryRequest
    ) {
        var loginUserId = Long.parseLong(loginUser.getUsername());
        var response = itineraryService.updateItinerary(tripId, itineraryId, loginUserId,itineraryRequest);
        return ResponseApi.OK(response);
    }

    @DeleteMapping("/{itineraryId}")
    public ResponseApi<?> deleteItinerary(
        @Parameter(name ="tripId", description = "TripId 입력", required = true, in = ParameterIn.PATH)
        @PathVariable
        Long tripId,
        @Parameter(name ="itineraryId", description = "Itinerary 입력", required = true, in = ParameterIn.PATH)
        @PathVariable
        Long itineraryId,
        @AuthenticationPrincipal User loginUser
    ) {
        var loginUserId = Long.parseLong(loginUser.getUsername());
        itineraryService.deleteItinerary(tripId, itineraryId, loginUserId);
        return ResponseApi.OK("삭제에 성공하였습니다.");
    }
}
