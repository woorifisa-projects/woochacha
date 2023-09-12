package com.woochacha.backend.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 입력 값이 입력되었습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "정보를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러가 발생하였습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
