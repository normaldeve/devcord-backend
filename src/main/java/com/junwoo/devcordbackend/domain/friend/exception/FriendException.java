package com.junwoo.devcordbackend.domain.friend.exception;

import com.junwoo.devcordbackend.common.exception.DomainException;
import com.junwoo.devcordbackend.common.exception.ErrorCode;

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
