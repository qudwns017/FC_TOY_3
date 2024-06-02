package org.group6.travel.domain.trip.model.enums;

import lombok.Getter;

@Getter
public enum DomesticType {
    DOMESTIC(0),
    OVERSEAS(1);

    private final int status;

    DomesticType(int status){
        this.status = status;
    }
}
