package com.woochacha.backend.domain.car.exception;

import com.woochacha.backend.exception.errorcode.CustomErrorCode;
import com.woochacha.backend.exception.exception.RestApiException;

public class CarNotFound extends RestApiException {
    public CarNotFound() {
        super(CustomErrorCode.CAR_IS_NOT_FOUND);
    }
}
