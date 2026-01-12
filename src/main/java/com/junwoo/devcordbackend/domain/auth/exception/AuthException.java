package com.junwoo.devcordbackend.domain.auth.exception;

import com.junwoo.devcordbackend.config.exception.DomainException;
import com.junwoo.devcordbackend.config.exception.ErrorCode;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 12.
 */
public class AuthException extends DomainException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
