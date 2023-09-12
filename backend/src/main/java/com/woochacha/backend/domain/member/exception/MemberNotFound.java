package com.woochacha.backend.domain.member.exception;

import com.woochacha.backend.exception.errorcode.CustomErrorCode;
import com.woochacha.backend.exception.exception.RestApiException;

public class MemberNotFound extends RestApiException {

    public MemberNotFound() {
        super(CustomErrorCode.USER_IS_NOT_FOUND);
    }
}
