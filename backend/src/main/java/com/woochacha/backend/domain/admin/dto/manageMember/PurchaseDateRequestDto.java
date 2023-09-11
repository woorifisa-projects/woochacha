package com.woochacha.backend.domain.admin.dto.manageMember;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class PurchaseDateRequestDto {
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate meetingDate;

        public PurchaseDateRequestDto() {}
        public PurchaseDateRequestDto(LocalDate meetingDate) {
                this.meetingDate = meetingDate;
        }
}
