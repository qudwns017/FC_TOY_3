package org.group6.travel.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorCodeIfs {

    OK(200, 200, "성공"),
    SUCCESS(202,202,"POST|PUT 성공"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청"),
    AUTH_NOT_EXIST(401, 401, "권한이 없습니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST.value(), 402, "잘못된 입력입니다. 입력된 날짜가 다른 정보와 적합한지 확인해주세요."),
    TRIP_NOT_EXIST(HttpStatus.NOT_FOUND.value(), 404, "TRIP NOT EXIST"),
    ITINERARY_NOT_EXIST(HttpStatus.NOT_FOUND.value(), 404, "TRIP NOT EXIST"),
    REPLY_NOT_EXIST(HttpStatus.NOT_FOUND.value(), 404,"REPLY_NOT_FOUND"),
    LOCATION_NOT_EXIST(HttpStatus.NOT_FOUND.value(), 404,"위치 결과 없음"),
    USER_NOT_EXIST(HttpStatus.NOT_FOUND.value(), 404, "USER NOT EXIST"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "서버 에러입니다."),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512, "Null Point"),
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;

}
