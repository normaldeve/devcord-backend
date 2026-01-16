package com.junwoo.devcordbackend.domain.user.exception;

import com.junwoo.devcordbackend.common.exception.DomainException;
import com.junwoo.devcordbackend.common.exception.ErrorCode;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 12.
 */
public class UserException extends DomainException {
    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
