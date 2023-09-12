package com.woochacha.backend.domain.mypage.exception;

import com.woochacha.backend.exception.errorcode.CustomErrorCode;
import com.woochacha.backend.exception.exception.RestApiException;

public class MypageNotFound extends RestApiException {
    public MypageNotFound() {
        super(CustomErrorCode.RESOURCE_NOT_FOUND);
    }
}
