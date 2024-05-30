package org.group6.travel.common.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.group6.travel.common.api.Api;
import org.group6.travel.common.api.ResponseApi;
import org.group6.travel.common.exception.ApiException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseApi<?> apiException(
        ApiException apiException
    ) {
        log.error("", apiException);

        var errorCode = apiException.getErrorCodeIfs();
        log.info(apiException.getErrorCodeIfs().getDescription());
        return ResponseApi.ERROR(errorCode, apiException.getErrorDescription());
    }
}