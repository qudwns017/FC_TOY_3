package org.group6.travel.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode implements StatusCodeIfs{
    OK(200, "성공"),
    SUCCESS(202,"POST|PUT 성공");

    private final Integer errorCode;
    private final String description;
}
