package com.woochacha.backend.domain.mypage.service;

import com.woochacha.backend.domain.AmazonS3.dto.AmazonS3RequestDto;
import com.woochacha.backend.domain.mypage.dto.*;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface MypageService {
    Page<ProductResponseDto> getRegisteredProductsByMemberId(Long memberId, int pageNumber, int pageSize);

    Page<ProductResponseDto> getSoldProductsByMemberId(Long memberId, int pageNumber, int pageSize);

    Page<ProductResponseDto> getPurchaseProductsByMemberId(Long memberId, int pageNumber, int pageSize);

    ProfileDto getProfileByMemberId(Long memberId);

    Page<SaleFormDto> getSaleFormsByMemberId(Long memberId, int pageNumber, int pageSize);

    Page<ProductResponseDto> getPurchaseRequestByMemberId(Long memberId, int pageNumber, int pageSize);

    EditProductDto getProductEditRequestInfo(Long memberId, Long productId);
    String editProductEditRequestInfo(Long memberId, Long productId, UpdatePriceDto updatePriceDto);

    String productDeleteRequest(Long productId, Long memberId);

    EditProfileDto getProfileForEdit(Long memberId);

    String editProfile(Long memberId, AmazonS3RequestDto amazonS3RequestDto) throws IOException;
}
