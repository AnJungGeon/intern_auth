package com.intren.auth.user.domain.exception;

import com.intren.auth.common.exception.BusinessException;


public class UserDuplicatedException extends BusinessException {

    public UserDuplicatedException() {
        super(UserErrorCode.USER_ALREADY_EXISTS);
    }

}
