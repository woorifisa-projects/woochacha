package com.woochacha.backend.domain.admin.dto;


import com.woochacha.backend.domain.admin.dto.detail.RegisterProductBasicInfo;
import com.woochacha.backend.domain.admin.dto.detail.RegisterProductDetailInfo;
import com.woochacha.backend.domain.admin.dto.detail.RegisterProductOptionInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RegisterProductDto {

    private RegisterProductBasicInfo registerProductBasicInfo; // 차량 기본 정보

    private RegisterProductDetailInfo registerProductDetailInfo; // 차량 상세 정보

    private List<RegisterProductOptionInfo> registerProductOptionInfos; // 옵션
}
