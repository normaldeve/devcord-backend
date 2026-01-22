package com.junwoo.devcordbackend.common.exception;

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
    CANNOT_REQUEST_SELF(HttpStatus.BAD_REQUEST.value(), "자기 자신에게 친구 요청을 보낼 수 없습니다"),
    ALREADY_FRIEND(HttpStatus.BAD_REQUEST.value(), "이미 친구 등록을 완료했습니다"),
    ALREADY_REQUEST(HttpStatus.BAD_REQUEST.value(), "이미 친구 요청을 보냈습니다"),
    FRIEND_BLOCKED(HttpStatus.BAD_REQUEST.value(), "친구 차단 상태입니다"),
    CANNOT_FOUND_FRIEND_REQUEST(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 친구 요청입니다"),
    ALREADY_COMPLETED_FRIEND_REQUEST(HttpStatus.BAD_REQUEST.value(), "이미 처리된 친구 요청입니다"),
    ALREADY_SERVER_MEMBER(HttpStatus.BAD_REQUEST.value(), "이미 서버에 속한 멤버입니다"),
    DUPLICATE_SERVER_INVITE(HttpStatus.BAD_REQUEST.value(), "중복된 서버 초대 요청입니다."),
    SERVER_INVITE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "서버 초대를 찾을 수 없습니다"),

    ALREADY_EXISTS_NICKNAME(HttpStatus.CONFLICT.value(), "이미 존재하는 닉네임입니다"),
    ALREADY_EXISTS_EMAIL(HttpStatus.CONFLICT.value(), "이미 존재하는 이메일입니다"),

    EMPTY_FILE(HttpStatus.BAD_REQUEST.value(), "업로드할 파일이 없습니다"),
    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST.value(), "파일 크기는 10MB를 초과할 수 없습니다"),
    INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST.value(), "이미지 파일만 업로드 가능합니다"),
    INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 파일 확장자입니다"),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "파일 업로드에 실패했습니다"),
    FILE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "파일 삭제에 실패했습니다");

    private final int code;
    private final String messge;

    ErrorCode(int code, String message) {
        this.code = code;
        this.messge = message;
    }
}
