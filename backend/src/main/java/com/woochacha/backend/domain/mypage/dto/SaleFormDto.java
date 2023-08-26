package com.woochacha.backend.domain.mypage.dto;

import com.woochacha.backend.domain.sale.entity.Branch;
import com.woochacha.backend.domain.status.entity.CarStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
public class SaleFormDto {

    private String carNum; // 차량 번호
    private LocalDateTime createdAt; // 신청폼 작성일
    private String branch; // 신청폼 작성시 선택한 차고지
    private String carStatus; // 신청폼 상태 (반려, 심사중, 심사완료)

    public SaleFormDto(String carNum, LocalDateTime createdAt, String branch, String carStatus) {
        this.carNum = carNum;
        this.createdAt = createdAt;
        this.branch = branch;
        this.carStatus = carStatus;
    }
}
