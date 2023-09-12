package com.woochacha.backend.domain.admin.exception;

import com.woochacha.backend.exception.errorcode.CustomErrorCode;
import com.woochacha.backend.exception.exception.RestApiException;

public class AdminNotFound extends RestApiException {
    public AdminNotFound() {
        super(CustomErrorCode.RESOURCE_NOT_FOUND);
    }
}
