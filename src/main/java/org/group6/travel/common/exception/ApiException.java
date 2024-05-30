package org.group6.travel.common.exception;

import lombok.Getter;
import org.group6.travel.common.error.ErrorCodeIfs;
import org.group6.travel.common.error.ErrorCodeNew;

@Getter
public class ApiException extends RuntimeException implements ApiExceptionIfs {

    private final ErrorCodeIfs errorCode;
    private final String errorDescription;

    public ApiException(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getDescription());
        this.errorCode = errorCodeIfs;
        this.errorDescription = errorCodeIfs.getDescription();
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, String errorDescription) {
        super(errorDescription);
        this.errorCode = errorCodeIfs;
        this.errorDescription = errorDescription;
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCodeIfs;
        this.errorDescription = errorCodeIfs.getDescription();
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable throwable, String errorDescription) {
        super(throwable);
        this.errorCode = errorCodeIfs;
        this.errorDescription = errorDescription;
    }

}
