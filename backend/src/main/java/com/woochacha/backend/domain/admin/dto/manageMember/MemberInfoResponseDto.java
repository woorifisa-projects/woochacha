package com.woochacha.backend.domain.admin.dto.manageMember;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoResponseDto {
    private MemberInfoDto memberInfoDto;
    private int onSale;
    private int completeSale;
    private int onPurchase;
    private int completePurchase;

    public MemberInfoResponseDto(MemberInfoDto memberInfoDto, int onSale, int completeSale, int onPurchase, int completePurchase) {
        this.memberInfoDto = memberInfoDto;
        this.onSale = onSale;
        this.completeSale = completeSale;
        this.onPurchase = onPurchase;
        this.completePurchase = completePurchase;
    }
}
