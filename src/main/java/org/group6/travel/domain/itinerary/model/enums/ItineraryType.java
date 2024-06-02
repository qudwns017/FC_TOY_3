package org.group6.travel.domain.itinerary.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItineraryType {
    MOVE(0),
    STAY(1);

    private final int value;
}
