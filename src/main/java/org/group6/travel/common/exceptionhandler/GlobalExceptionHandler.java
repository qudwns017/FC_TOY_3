package org.group6.travel.common.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.group6.travel.common.api.Api;
import org.group6.travel.common.api.ResponseApi;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.error.ErrorCodeNew;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MAX_VALUE)       // 가장 마지막에 실행 적용
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseApi<?> exception(
        Exception exception
    ) {
        log.error("", exception);

        return ResponseApi.ERROR(ErrorCodeNew.SERVER_ERROR);
    }
}
