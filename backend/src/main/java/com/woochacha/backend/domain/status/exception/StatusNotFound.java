package com.woochacha.backend.domain.status.exception;

import com.woochacha.backend.exception.errorcode.CustomErrorCode;
import com.woochacha.backend.exception.exception.RestApiException;

public class StatusNotFound extends RestApiException {

    public StatusNotFound() {
        super(CustomErrorCode.STATUS_IS_NOT_FOUND);
    }
}
