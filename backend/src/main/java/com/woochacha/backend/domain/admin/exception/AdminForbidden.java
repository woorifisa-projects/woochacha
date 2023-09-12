package com.woochacha.backend.domain.admin.exception;

import com.woochacha.backend.exception.errorcode.CustomErrorCode;
import com.woochacha.backend.exception.exception.RestApiException;

public class AdminForbidden extends RestApiException {
    public AdminForbidden() {
        super(CustomErrorCode.AUTHORIZATION_EXCEPTION);
    }
}
