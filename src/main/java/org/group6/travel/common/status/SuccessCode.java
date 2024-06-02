package org.group6.travel.common.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode implements StatusCodeIfs{
    OK(200, "성공"),
    POST(201, "추가 성공"),
    PUT(202,"수정 성공"),
    UNLIKE(203, "좋아요 취소"),
    DELETE(204, "삭제 성공");

    private final Integer Code;
    private final String description;
}
