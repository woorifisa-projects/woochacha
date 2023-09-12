package com.woochacha.backend.domain.admin.exception;

import com.woochacha.backend.exception.errorcode.CustomErrorCode;
import com.woochacha.backend.exception.exception.RestApiException;

public class AdminNotAuth extends RestApiException {
    public AdminNotAuth() {
        super(CustomErrorCode.LOGIN_DO_NOT_MATCH);
    }
}
