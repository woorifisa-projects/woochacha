package com.woochacha.backend.domain.admin.dto.detail;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterProductExchangeInfo {
    private String type;
    private String date;
}
