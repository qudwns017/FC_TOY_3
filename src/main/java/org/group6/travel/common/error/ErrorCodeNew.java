package org.group6.travel.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCodeNew implements StatusCodeIfs{

    BAD_REQUEST(400, "잘못된 요청"),
    AUTH_NOT_EXIST(401, "권한이 없습니다."),
    TIME_ERROR(402, "잘못된 입력입니다. 입력된 날짜가 다른 정보와 적합한지 확인해주세요."),
    TRIP_NOT_EXIST(404, "TRIP NOT EXIST"),
    ITINERARY_NOT_EXIST(404, "TRIP NOT EXIST"),
    REPLY_NOT_EXIST(404,"REPLY_NOT_FOUND"),
    LOCATION_NOT_EXIST(404,"위치 결과 없음"),
    USER_NOT_EXIST(404, "USER NOT EXIST"),
    SERVER_ERROR(500, "서버 에러입니다."),
    NULL_POINT(512, "Null Point"),

    INVALID_TOKEN(2000 , "유효하지 않은 토큰"),
    EXPIRED_TOKEN(2001, "만료된 토큰"),
    TOKEN_EXCEPTION(2002, "토큰 알수없는 에러"),
    AUTHORIZATION_TOKEN_NOT_FOUND(2003, "인증 헤더 토큰 없음"),

    USER_NOT_FOUND (1404, "USER_NOT_EXIST"),
    INVALID_PASSWORD(1404, "INVALID_PASSWORD")
    ;
    private final Integer errorCode;
    private final String description;
}
