package com.woochacha.backend.domain.mypage.dto;

import com.woochacha.backend.domain.member.entity.Member;
import com.woochacha.backend.domain.sale.entity.Branch;
import com.woochacha.backend.domain.status.entity.CarStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SaleFormDto {

    private String carNum; // 차량 번호
    private LocalDateTime createdAt; // 신청폼 작성일
    private Branch branch; // 신청폼 작성시 선택한 차고지
    private CarStatus carStatus; // 신청폼 상태 (반려, 심사중, 심사완료)

    public SaleFormDto(String carNum, LocalDateTime createdAt, Branch branch, CarStatus carStatus) {
        this.carNum = carNum;
        this.createdAt = createdAt;
        this.branch = branch;
        this.carStatus = carStatus;
    }
}
