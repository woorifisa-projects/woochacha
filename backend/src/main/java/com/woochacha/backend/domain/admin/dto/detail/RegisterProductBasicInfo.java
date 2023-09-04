package com.woochacha.backend.domain.admin.dto.detail;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterProductBasicInfo {
    private String title; // 제목 : 모델+차명+연식

    private String carName; // 차량명

    private String carNum; // 차량 번호

    private String branch; // 지점
}
