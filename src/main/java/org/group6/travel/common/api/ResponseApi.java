package org.group6.travel.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group6.travel.common.status.*;

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
        api.status = SuccessCode.OK.getCode();
        api.message = SuccessCode.OK.getDescription();
        api.data = data;
        return api;
    }

    public static <T> ResponseApi<T> OK(T data, SuccessCode successCode) {
        var api = new ResponseApi<T>();
        api.status = successCode.getCode();
        api.message = successCode.getDescription();
        api.data = data;
        return api;
    }

    public static ResponseApi<Object> ERROR(ErrorCode errorCode) {
        var api = new ResponseApi<Object>("error");
        api.status = errorCode.getCode();
        api.message = errorCode.getDescription();
        return api;
    }

    public static ResponseApi<Object> ERROR(StatusCodeIfs errorCodeIfs, Throwable throwable) {
        var api = new ResponseApi<Object>("error");
        api.status = errorCodeIfs.getCode();
        api.message = errorCodeIfs.getDescription();
        return api;
    }

    public static ResponseApi<Object> ERROR(StatusCodeIfs errorCodeIfs, String description) {
        var api = new ResponseApi<Object>("error");
        api.status = errorCodeIfs.getCode();
        api.message = description;
        return api;
    }
}
