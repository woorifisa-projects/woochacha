package com.woochacha.backend.domain.log.exception;

import com.woochacha.backend.exception.errorcode.CustomErrorCode;
import com.woochacha.backend.exception.exception.RestApiException;

public class LogNotFound extends RestApiException {

    public LogNotFound( ) {
        super(CustomErrorCode.LOG_IS_NOT_FOUND);
    }
}
