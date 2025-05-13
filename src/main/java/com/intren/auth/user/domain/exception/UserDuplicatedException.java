package com.intren.auth.user.domain.exception;

import com.intren.auth.common.exception.BusinessException;
import com.intren.auth.common.exception.CommonErrorCode;
import com.intren.auth.common.exception.ErrorCode;

public class UserDuplicatedException extends BusinessException {

    public UserDuplicatedException() {
        super(UserErrorCode.USER_ALREADY_EXISTS);
    }

}
