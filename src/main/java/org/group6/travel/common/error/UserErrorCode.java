package org.group6.travel.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCodeIfs {

    USER_NOT_FOUND(400, 1404, "USER_NOT_EXIST"),
    INVALID_PASSWORD(400, 1404, "INVALID_PASSWORD")
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
