package com.intren.auth.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    DELETED_DATA_ACCESS(HttpStatus.BAD_REQUEST, "삭제된 리소스에 접근 불가입니다"),
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "이미 존재하는 리소스입니다."),
    INTERNAL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 파라미터가 전달되었습니다."),
    OPERATION_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "현재 상태에서 수행할 수 없는 작업입니다."),
    UNMATCHED_USER_DATA(HttpStatus.FORBIDDEN, "요청자의 데이터가 아닙니다."),
    RESOURCES_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
