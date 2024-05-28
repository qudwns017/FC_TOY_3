package org.group6.travel.domain.itinerary.model.enums;

import lombok.Getter;

@Getter
public enum ItineraryType {
    MOVE("이동", 0),
    STAY("체류", 1);

    private String s;
    private int value;

    ItineraryType(String s, int value){
        this.value = value;
        this.s = s;
    }
}
