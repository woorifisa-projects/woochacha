package com.woochacha.backend.domain.sale.exception;

import com.woochacha.backend.exception.errorcode.CustomErrorCode;
import com.woochacha.backend.exception.exception.RestApiException;

public class AlreadySubmitSaleForm extends RestApiException {
    public AlreadySubmitSaleForm() {
        super(CustomErrorCode.ALREADY_REJISTERED_CARNUM);
    }
}
