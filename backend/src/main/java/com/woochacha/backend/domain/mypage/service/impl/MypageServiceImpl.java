package com.woochacha.backend.domain.mypage.service.impl;

import com.woochacha.backend.common.ModelMapping;
import com.woochacha.backend.domain.AmazonS3.dto.AmazonS3RequestDto;
import com.woochacha.backend.domain.AmazonS3.service.AmazonS3Service;
import com.woochacha.backend.domain.log.service.LogService;
import com.woochacha.backend.domain.member.entity.Member;
import com.woochacha.backend.domain.member.repository.MemberRepository;
import com.woochacha.backend.domain.mypage.dto.*;
import com.woochacha.backend.domain.mypage.repository.MypageRepository;
import com.woochacha.backend.domain.mypage.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {

    private final MypageRepository mypageRepository;
    private final MemberRepository memberRepository;
    private final AmazonS3Service amazonS3Service;
    private final ModelMapper modelMapper = ModelMapping.getInstance();

    private final LogService logService;

    // JPQL로 조회한 결과 ProductResponseDto로 변환
    private ProductResponseDto arrayToProductResponseDto(Object[] array) {
        // 향후 switch문으로 리팩토링 예정
        String status;
        if (array[6].toString().equals("4")){
            status = "판매중";
        } else if (array[6].toString().equals("6")) {
            status = "삭제신청완료";
        } else if (array[6].toString().equals("5")){
            status = "판매완료";
        } else {
            status = "수정신청완료";
        }
        return ProductResponseDto.builder()
                .title((String) array[0])
                .distance((Integer) array[1])
                .branch((String) array[2])
                .price((Integer) array[3])
                .imageUrl((String) array[4])
                .productId((Long) array[5])
                .status(status)
                .build();
    }

    // JPQL로 조회한 결과 PurchaseReqeustListDto로 변환
    private ProductResponseDto arrayToPurchaseReqeustListDto(Object[] array){
        String status;
        if (array[5].toString().equals("0")){
            status = "미검토";
        }else{
            status = "검토";
        }
        return ProductResponseDto.builder()
                .title((String) array[0])
                .distance((Integer) array[1])
                .branch((String) array[2])
                .price((Integer) array[3])
                .imageUrl((String) array[4])
                .productId((Long) array[5])
                .status(status)
                .build();
    }

    // JPQL로 조회한 결과 SaleFormDto로 변환해서 전달
    private SaleFormDto arrayToSaleFormDto(Object[] array) {
        return SaleFormDto.builder()
                .carNum((String) array[0])
                .createdAt((LocalDateTime) array[1])
                .branch((String) array[2])
                .carStatus((String) array[3])
                .build();
    }

    // 등록한 매물 조회
    public Page<ProductResponseDto> getRegisteredProductsByMemberId(Long memberId, int pageNumber, int pageSize) throws Exception {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        try {
            Page<Object[]> productsPage = mypageRepository.getRegisteredProductsByMemberId(memberId, pageable);
            return productsPage.map(this::arrayToProductResponseDto);
        }catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    // 판매 이력 조회
    public Page<ProductResponseDto> getSoldProductsByMemberId(Long memberId, int pageNumber, int pageSize) throws Exception {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        try {
            Page<Object[]> productsPage = mypageRepository.getSoldProductsByMemberId(memberId, pageable);

            return productsPage.map(this::arrayToProductResponseDto);
        }catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    // 구매 이력 조회
    public Page<ProductResponseDto> getPurchaseProductsByMemberId(Long memberId, int pageNumber, int pageSize) throws Exception {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        try{
            Page<Object[]> productsPage = mypageRepository.getPurchaseProductsByMemberId(memberId, pageable);

            return productsPage.map(this::arrayToProductResponseDto);
        }catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        }

    }

    // 프로필 조회
    public ProfileDto getProfileByMemberId(Long memberId) {
        try{
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
            return modelMapper.map(member, ProfileDto.class);
        }catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        }

    }

    // 판매 신청 폼 조회
    public Page<SaleFormDto> getSaleFormsByMemberId(Long memberId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        try{
            Page<Object[]> saleFormsPage = mypageRepository.getSaleFormsByMemberId(memberId, pageable);
            Page<SaleFormDto> saleFormDtosPage = saleFormsPage.map(this::arrayToSaleFormDto);
            return saleFormDtosPage;
        }catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    // 구매 신청 폼 조회
    public Page<ProductResponseDto> getPurchaseRequestByMemberId(Long memberId, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        try {
            Page<Object[]> purchaseRequestPage = mypageRepository.getPurchaseRequestByMemberId(memberId, pageable);
            return purchaseRequestPage.map(this::arrayToPurchaseReqeustListDto);
        } catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    // 수정신청 폼 데이터 가져오기
    public EditProductDto getProductEditRequestInfo(Long memberId, Long productId){
        try {
            EditProductDto editProductDto = mypageRepository.getProductEditRequestInfo(memberId, productId);
            return editProductDto;
        }catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    // 수정신청 폼 제출
    @Transactional
    public void updatePrice(Long productId, Integer updatePrice, Long memberId){
        try {
            mypageRepository.updatePrice(productId, updatePrice);
            logService.savedMemberLogWithTypeAndEtc(memberId, "상품 가격 수정 요청", "/product/detail/" + productId);
        }catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    // 등록한 매물 삭제 신청
    @Transactional
    public void productDeleteRequest(Long productId, Long memberId){
        try {
            mypageRepository.requestProductDelete(productId);
            logService.savedMemberLogWithTypeAndEtc(memberId, "상품 삭제 요청", "/product/detail/" + productId);
        }catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    // 프로필 수정 (GET요청 시 데이터 보여주기)
    public EditProfileDto getProfileForEdit(Long memberId){
        try {
            Member member = memberRepository.findById(memberId).get();
            EditProfileDto editProdileDto = EditProfileDto.builder()
                    .name(member.getName())
                    .imageUrl(member.getProfileImage())
                    .build();
            return editProdileDto;
        }catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    // 프로필 수정 (PATCH요청 시 데이터 수정)
    @Transactional
    public String editProfile(Long memberId, AmazonS3RequestDto amazonS3RequestDto) throws IOException {
        try {
            String email = memberRepository.findById(memberId).get().getEmail();
            amazonS3RequestDto.setEmail(email);
            String newProfileIamge = amazonS3Service.uploadProfile(amazonS3RequestDto);
            // TODO: multipartfile null 해결

            logService.savedMemberLogWithType(memberId, "프로필 이미지 수정");
            return newProfileIamge;
        }catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}

