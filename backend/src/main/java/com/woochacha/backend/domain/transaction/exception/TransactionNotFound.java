package com.woochacha.backend.domain.transaction.exception;

import com.woochacha.backend.exception.errorcode.CustomErrorCode;
import com.woochacha.backend.exception.exception.RestApiException;

public class TransactionNotFound extends RestApiException {

    public TransactionNotFound() {
        super(CustomErrorCode.TRANSACTION_IS_NOT_FOUND);
    }
}
