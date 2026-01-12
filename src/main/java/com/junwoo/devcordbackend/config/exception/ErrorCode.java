package com.junwoo.devcordbackend.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 12.
 */
@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부 에러 발생");

    private final int code;
    private final String messge;

    ErrorCode(int code, String message) {
        this.code = code;
        this.messge = message;
    }
}
