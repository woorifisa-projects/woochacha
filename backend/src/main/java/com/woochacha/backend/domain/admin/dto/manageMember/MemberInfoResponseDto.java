package com.woochacha.backend.domain.admin.dto.manageMember;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoResponseDto {
    private GetMemberDto getMemberDto;
    private int onSale;
    private int completeSale;
    private int onPurchase;
    private int completePurchase;

    public MemberInfoResponseDto(GetMemberDto getMemberDto,int onSale, int completeSale, int onPurchase, int completePurchase) {
        this.getMemberDto = getMemberDto;
        this.onSale = onSale;
        this.completeSale = completeSale;
        this.onPurchase = onPurchase;
        this.completePurchase = completePurchase;
    }
}
