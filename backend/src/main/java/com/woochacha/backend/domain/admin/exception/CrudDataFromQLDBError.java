package com.woochacha.backend.domain.admin.exception;

import com.woochacha.backend.exception.errorcode.CustomErrorCode;
import com.woochacha.backend.exception.exception.RestApiException;

public class CrudDataFromQLDBError extends RestApiException {
    public CrudDataFromQLDBError() {
        super(CustomErrorCode.CRUDE_QLDB_ERROR);
    }
}
