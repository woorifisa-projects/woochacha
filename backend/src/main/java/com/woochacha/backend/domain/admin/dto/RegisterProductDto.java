package com.woochacha.backend.domain.admin.dto;


import com.woochacha.backend.domain.admin.dto.detail.RegisterProductBasicInfo;
import com.woochacha.backend.domain.admin.dto.detail.RegisterProductDetailInfo;
import com.woochacha.backend.domain.admin.dto.detail.RegisterProductOptionInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

// 매물 등록 전 QLDB에서 데이터를 조회하기 위한 Dto
@Getter
@Builder
public class RegisterProductDto {

    private RegisterProductBasicInfo registerProductBasicInfo; // 차량 기본 정보

    private RegisterProductDetailInfo registerProductDetailInfo; // 차량 상세 정보

    private List<RegisterProductOptionInfo> registerProductOptionInfos; // 옵션
}
