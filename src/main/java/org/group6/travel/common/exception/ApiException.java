package org.group6.travel.common.exception;

import lombok.Getter;
import org.group6.travel.common.error.StatusCodeIfs;

@Getter
public class ApiException extends RuntimeException implements ApiExceptionIfs {

    private final StatusCodeIfs errorCode;
    private final String errorDescription;

    public ApiException(StatusCodeIfs errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorDescription = errorCode.getDescription();
    }

    public ApiException(StatusCodeIfs errorCode, String errorDescription) {
        super(errorDescription);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public ApiException(StatusCodeIfs errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.errorDescription = errorCode.getDescription();
    }

    public ApiException(StatusCodeIfs errorCode, Throwable throwable, String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

}
