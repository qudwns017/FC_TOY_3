package org.group6.travel.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements StatusCodeIfs{

    BAD_REQUEST(400, "잘못된 요청"),
    AUTH_NOT_EXIST(401, "권한이 없습니다."),
    TIME_ERROR(402, "잘못된 입력입니다. 입력된 날짜가 다른 정보와 적합한지 확인해주세요."),
    TRIP_NOT_EXIST(404, "여행 정보가 존재하지 않습니다."),
    ITINERARY_NOT_EXIST(404, "여정 정보가 존재하지 않습니다."),
    REPLY_NOT_EXIST(404,"댓글 정보가 존재하지 않습니다."),
    LOCATION_NOT_EXIST(404,"위치 결과가 존재하지 않습니다."),
    USER_NOT_EXIST(404, "유저 정보가 존재하지 않습니다."),
    USER_NOT_MATCH(404,"유저정보가 일치하지 않습니다."),
    SERVER_ERROR(500, "서버 에러입니다."),
    NULL_POINT(512, "Null Point"),

    INVALID_TOKEN(2000 , "유효하지 않은 토큰"),
    EXPIRED_TOKEN(2001, "Token이 만료되었습니다."),
    EXPIRED_ACCESS_TOKEN(2002, "Access Token이 만료되었습니다."),
    EXPIRED_REFRESH_TOKEN(2003, "Refresh Token이 만료되었습니다. 다시 로그인을 해주세요."),
    TOKEN_EXCEPTION(2004, "토큰 알수없는 에러"),
    AUTHORIZATION_TOKEN_NOT_FOUND(2005, "인증 헤더 토큰 없음"),

    USER_NOT_FOUND (1404, "USER_NOT_EXIST"),
    INVALID_PASSWORD(1404, "INVALID_PASSWORD")
    ;
    private final Integer errorCode;
    private final String description;
}
