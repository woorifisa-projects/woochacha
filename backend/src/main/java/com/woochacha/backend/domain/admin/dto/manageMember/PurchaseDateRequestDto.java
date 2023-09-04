package com.woochacha.backend.domain.admin.dto.manageMember;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class PurchaseDateRequestDto {
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate meetingDate;
}
