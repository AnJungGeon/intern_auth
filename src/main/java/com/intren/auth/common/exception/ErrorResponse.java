package com.intren.auth.common.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final ErrorDetail error;

    public ErrorResponse(ErrorCode errorCode){
        this.error = new ErrorDetail(errorCode.name(), errorCode.getMessage());
    }
}
