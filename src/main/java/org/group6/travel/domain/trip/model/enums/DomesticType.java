package org.group6.travel.domain.trip.model.enums;

import lombok.Getter;

@Getter
public enum DomesticType {
    DOMESTIC("국내", 0),
    OVERSEAS("해외", 0);

    private final String title;
    private final int value;

    DomesticType(String title, int value){
        this.title = title;
        this.value = value;
    }
}
