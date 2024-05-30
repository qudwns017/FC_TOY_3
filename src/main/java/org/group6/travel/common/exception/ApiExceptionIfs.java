package org.group6.travel.common.exception;

import org.group6.travel.common.error.StatusCodeIfs;

public interface ApiExceptionIfs {

    StatusCodeIfs getErrorCode();
    String getErrorDescription();

}

