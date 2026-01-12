package com.junwoo.devcordbackend.config.exception;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 12.
 */
@Getter
public class DomainException extends RuntimeException {

    private final LocalDateTime timestamp;
    private final ErrorCode errorCode;
    private final Map<String, Object> details;

    public DomainException(ErrorCode errorCode) {
        super(errorCode.getMessge());
        this.timestamp = LocalDateTime.now();
        this.errorCode = errorCode;
        this.details = new HashMap<>();
    }

    public DomainException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessge(), cause);
        this.timestamp = LocalDateTime.now();
        this.errorCode = errorCode;
        this.details = new HashMap<>();
    }

    public void addDetail(String key, Object value) {
        this.details.put(key, value);
    }
}
