package org.group6.travel.domain.user.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreUserRole {

    MASTER("마스터"),
    ADMIN("관리자"),
    USER("일반유저"),
    ;

    private String description;
}
