package org.group6.travel.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorCodeIfs {

    OK(200, 200, "성공"),
    SUCCESS(201,201,"성공"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "서버 에러"),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512, "Null Point"),
    TRIP_NOT_EXIST(HttpStatus.NOT_FOUND.value(), 412, "TRIP NOT EXIST"),
    REPLY_NOT_EXIST(HttpStatus.NOT_FOUND.value(), 413,"REPLY_NOT_FOUND"),
    LOCATION_NOT_EXIST(HttpStatus.NOT_FOUND.value(), 414,"위치 결과 없음")
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;

}
