package com.woochacha.backend.domain.admin.dto.detail;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterProductOptionInfo {
    private String option;
    private Byte whether;

    public RegisterProductOptionInfo(String option, Byte whether) {
        this.option = option;
        this.whether = whether;
    }
}
