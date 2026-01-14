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
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부 에러 발생"),

    UNSUCCESSFUL_AUTHENTICATION(HttpStatus.UNAUTHORIZED.value(), "이메일 또는 비밀번호가 일치하지 않습니다"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 Refresh Token입니다"),
    CANNOT_FOUND_USER(HttpStatus.BAD_REQUEST.value(), "사용자를 찾을 수 없습니다"),

    CHATTING_SERVER_DENIED(HttpStatus.FORBIDDEN.value(), "해당 서버에 접근할 권한이 없습니다"),
    DUPLICATE_CHANNEL_NAME(HttpStatus.CONTINUE.value(), "이미 존재하는 채널 이름입니다"),
    CHANNEL_NOT_IN_SERVER(HttpStatus.CONFLICT.value(), "채널이 해당 서버에 속하지 않습니다"),
    NOT_A_SERVER_MEMBER(HttpStatus.UNAUTHORIZED.value(), "서버에 속해 있는 멤버가 아닙니다"),

    ALREADY_EXISTS_EMAIL(HttpStatus.CONFLICT.value(), "이미 존재하는 이메일입니다"),
    ;

    private final int code;
    private final String messge;

    ErrorCode(int code, String message) {
        this.code = code;
        this.messge = message;
    }
}
