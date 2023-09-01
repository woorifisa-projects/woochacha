package com.woochacha.backend.domain.admin.service;

import com.woochacha.backend.domain.admin.dto.approve.RegisterProductDto;

public interface RegisterProductService {

    RegisterProductDto getRegisterProductInfo(Long saleFormId);
}
