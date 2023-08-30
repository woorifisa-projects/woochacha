package com.woochacha.backend.domain.admin.dto.detail;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterProductAccidentInfo {
    private String type;
    private String date;
}
