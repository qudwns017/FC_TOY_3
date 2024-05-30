package org.group6.travel.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.error.ErrorCodeIfs;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseApi<T> {
    private Integer status;
    private String message;

    private T data;

    public ResponseApi (T init) {
        this.data = init;
    }

    public static <T> ResponseApi<T> OK(T data) {
        var api = new ResponseApi<T>();
        api.status = ErrorCode.OK.getErrorCode();
        api.message = ErrorCode.OK.getDescription();
        api.data = data;
        return api;
    }

    public static <T> ResponseApi<T> SUCCESS(T data) {
        var api = new ResponseApi<T>();
        api.status = ErrorCode.SUCCESS.getErrorCode();
        api.message = ErrorCode.SUCCESS.getDescription();
        api.data = data;
        return api;
    }

    public static ResponseApi<Object> ERROR(ErrorCodeIfs errorCodeIfs) {
        var api = new ResponseApi<Object>("error");
        api.status = errorCodeIfs.getErrorCode();
        api.message = errorCodeIfs.getDescription();
        return api;
    }

    public static ResponseApi<Object> ERROR(ErrorCodeIfs errorCodeIfs, Throwable throwable) {
        var api = new ResponseApi<Object>("error");
        api.status = errorCodeIfs.getErrorCode();
        api.message = errorCodeIfs.getDescription();
        return api;
    }

    public static ResponseApi<Object> ERROR(ErrorCodeIfs errorCodeIfs, String description) {
        var api = new ResponseApi<Object>("error");
        api.status = errorCodeIfs.getErrorCode();
        api.message = description;
        return api;
    }
}
