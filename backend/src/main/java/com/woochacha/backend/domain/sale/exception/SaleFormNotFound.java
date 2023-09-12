package com.woochacha.backend.domain.sale.exception;

import com.woochacha.backend.exception.errorcode.CustomErrorCode;
import com.woochacha.backend.exception.errorcode.ErrorCode;
import com.woochacha.backend.exception.exception.RestApiException;

public class SaleFormNotFound extends RestApiException {

    public SaleFormNotFound(ErrorCode errorCode) {
        super(CustomErrorCode.SALE_IS_NOT_FOUND);
    }
}
