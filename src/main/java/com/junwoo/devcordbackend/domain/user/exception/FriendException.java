package com.junwoo.devcordbackend.domain.user.exception;

import com.junwoo.devcordbackend.config.exception.DomainException;
import com.junwoo.devcordbackend.config.exception.ErrorCode;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 14.
 */
public class FriendException extends DomainException {
    public FriendException(ErrorCode errorCode) {
        super(errorCode);
    }
}
