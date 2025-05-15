package com.intren.auth.authentication.domain.exception;

import com.intren.auth.common.exception.BusinessException;
import com.intren.auth.common.exception.ErrorCode;

public class AuthException extends BusinessException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
