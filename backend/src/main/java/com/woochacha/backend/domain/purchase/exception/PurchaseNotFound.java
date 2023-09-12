package com.woochacha.backend.domain.purchase.exception;

import com.woochacha.backend.exception.errorcode.CustomErrorCode;
import com.woochacha.backend.exception.exception.RestApiException;

public class PurchaseNotFound extends RestApiException {

    public PurchaseNotFound() {
        super(CustomErrorCode.PURCHASE_IS_NOT_FOUND);
    }
}
