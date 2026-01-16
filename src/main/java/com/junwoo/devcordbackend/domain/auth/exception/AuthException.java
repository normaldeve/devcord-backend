package com.junwoo.devcordbackend.domain.auth.exception;

import com.junwoo.devcordbackend.common.exception.DomainException;
import com.junwoo.devcordbackend.common.exception.ErrorCode;

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
