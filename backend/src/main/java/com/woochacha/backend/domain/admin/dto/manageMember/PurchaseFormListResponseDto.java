package com.woochacha.backend.domain.admin.dto.manageMember;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PurchaseFormListResponseDto {
    private Long productId;
    private String productUrl;
    private String carNum;
    private String buyerName;
    private String sellerName;
    private int purchaseStatus;
    private int transactionStatus;

    public PurchaseFormListResponseDto(Long productId, String productUrl, String carNum, String buyerName, String sellerName, int purchaseStatus, int transactionStatus) {
        this.productId = productId;
        this.productUrl = productUrl;
        this.carNum = carNum;
        this.buyerName = buyerName;
        this.sellerName = sellerName;
        this.purchaseStatus = purchaseStatus;
        this.transactionStatus = transactionStatus;
    }
}
