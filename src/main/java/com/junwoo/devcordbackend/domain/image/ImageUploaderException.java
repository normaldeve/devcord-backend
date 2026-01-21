package com.junwoo.devcordbackend.domain.image;

import com.junwoo.devcordbackend.common.exception.DomainException;
import com.junwoo.devcordbackend.common.exception.ErrorCode;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 21.
 */
public class ImageUploaderException extends DomainException {

    public ImageUploaderException(ErrorCode errorCode) {
        super(errorCode);
    }
}
