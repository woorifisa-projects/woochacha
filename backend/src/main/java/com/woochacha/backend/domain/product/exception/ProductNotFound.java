package com.woochacha.backend.domain.product.exception;

import com.woochacha.backend.exception.errorcode.CustomErrorCode;
import com.woochacha.backend.exception.exception.RestApiException;

public class ProductNotFound extends RestApiException {

    public ProductNotFound( ) {
        super(CustomErrorCode.PRODUCT_IS_NOT_FOUND);
    }
}
