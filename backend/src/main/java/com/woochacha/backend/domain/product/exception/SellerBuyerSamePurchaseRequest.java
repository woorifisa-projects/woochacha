package com.woochacha.backend.domain.product.exception;

import com.woochacha.backend.exception.errorcode.CustomErrorCode;
import com.woochacha.backend.exception.exception.RestApiException;

public class SellerBuyerSamePurchaseRequest extends RestApiException {
    public SellerBuyerSamePurchaseRequest() {
        super(CustomErrorCode.SELLER_BUYER_SAME);
    }
}

