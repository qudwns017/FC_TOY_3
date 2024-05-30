package org.group6.travel.common.exception;

import org.group6.travel.common.error.ErrorCodeIfs;
import org.group6.travel.common.error.ErrorCodeNew;

public interface ApiExceptionIfs {

    ErrorCodeIfs getErrorCode();
    String getErrorDescription();

}

