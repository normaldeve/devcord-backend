package com.junwoo.devcordbackend.domain.room.exception;

import com.junwoo.devcordbackend.common.exception.DomainException;
import com.junwoo.devcordbackend.common.exception.ErrorCode;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 13.
 */
public class ServerException extends DomainException {
    public ServerException(ErrorCode errorCode) {
        super(errorCode);
    }
}
