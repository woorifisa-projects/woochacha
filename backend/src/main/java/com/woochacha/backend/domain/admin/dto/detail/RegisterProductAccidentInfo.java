package com.woochacha.backend.domain.admin.dto.detail;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterProductAccidentInfo {
    private String type;
    private String date;
}
