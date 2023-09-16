package com.woochacha.backend.domain.admin.dto.manageMember;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class PurchaseMemberInfoResponseDto {
    private String sellerName;
    private String sellerPhone;
    private String buyerName;
    private String buyerPhone;

    public PurchaseMemberInfoResponseDto(String sellerName, String sellerPhone, String buyerName, String buyerPhone) {
        this.sellerName = sellerName;
        this.sellerPhone = sellerPhone;
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
    }
}
