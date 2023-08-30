package com.woochacha.backend.domain.admin.service;

import com.woochacha.backend.domain.admin.dto.RegisterProductDto;

public interface RegisterProductService {

    RegisterProductDto getRegisterProductInfo(Long saleFormId, String carNum);
}
