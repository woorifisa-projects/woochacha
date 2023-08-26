package com.woochacha.backend.domain.product.dto;

import com.woochacha.backend.domain.product.dto.detail.ProductBasicInfo;
import com.woochacha.backend.domain.product.dto.detail.ProductDetailInfo;
import com.woochacha.backend.domain.product.dto.detail.ProductOptionInfo;
import com.woochacha.backend.domain.product.dto.detail.ProductOwnerInfo;
import lombok.Data;

import java.util.List;

@Data
public class ProductDetailResponseDto {
    private ProductBasicInfo productBasicInfo; // 차량 기본 정보

    private ProductDetailInfo productDetailInfo; // 차량 상세 정보

    private List<ProductOptionInfo> productOptionInfo; // 옵션

    private ProductOwnerInfo productOwnerInfo; // 판매자 정보

    private List<String> carImageList; // 이미지 리스트

    public ProductDetailResponseDto(ProductBasicInfo productBasicInfo, ProductDetailInfo productDetailInfo, List<ProductOptionInfo> productOptionInfo, ProductOwnerInfo productOwnerInfo, List<String> carImageList) {
        this.productBasicInfo = productBasicInfo;
        this.productDetailInfo = productDetailInfo;
        this.productOptionInfo = productOptionInfo;
        this.productOwnerInfo = productOwnerInfo;
        this.carImageList = carImageList;
    }


}
