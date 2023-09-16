package com.woochacha.backend.domain.mypage.service.impl;

import com.woochacha.backend.common.DataMasking;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final Logger logger = LoggerFactory.getLogger(MypageServiceImpl.class);

    private final LogService logService;

    private final DataMasking dataMasking;

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
    @Override
    public Page<ProductResponseDto> getRegisteredProductsByMemberId(Long memberId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> productsPage = mypageRepository.getRegisteredProductsByMemberId(memberId, pageable);

        return productsPage.map(this::arrayToProductResponseDto);
    }

    // 판매 이력 조회
    @Override
    public Page<ProductResponseDto> getSoldProductsByMemberId(Long memberId, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> productsPage = mypageRepository.getSoldProductsByMemberId(memberId, pageable);

        return productsPage.map(this::arrayToProductResponseDto);
    }

    // 구매 이력 조회
    @Override
    public Page<ProductResponseDto> getPurchaseProductsByMemberId(Long memberId, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> productsPage = mypageRepository.getPurchaseProductsByMemberId(memberId, pageable);

        return productsPage.map(this::arrayToProductResponseDto);
    }

    // 프로필 조회
    @Override
    public ProfileDto getProfileByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        ProfileDto profileDto = modelMapper.map(member, ProfileDto.class);
        profileDto.setEmail(dataMasking.decoding(profileDto.getEmail()));
        profileDto.setPhone(dataMasking.decoding(profileDto.getPhone()));
        return profileDto;
    }

    // 판매 신청 폼 조회
    @Override
    public Page<SaleFormDto> getSaleFormsByMemberId(Long memberId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> saleFormsPage = mypageRepository.getSaleFormsByMemberId(memberId, pageable);
        Page<SaleFormDto> saleFormDtosPage = saleFormsPage.map(this::arrayToSaleFormDto);
        return saleFormDtosPage;
    }

    // 구매 신청 폼 조회
    @Override
    public Page<ProductResponseDto> getPurchaseRequestByMemberId(Long memberId, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> purchaseRequestPage = mypageRepository.getPurchaseRequestByMemberId(memberId, pageable);
        return purchaseRequestPage.map(this::arrayToPurchaseReqeustListDto);
    }

    @Override
    public EditProductDto getProductEditRequestInfo(Long memberId, Long productId){
        return mypageRepository.getProductEditRequestInfo(memberId, productId);
    }

    // 수정신청 폼 데이터 가져오기
    @Transactional
    @Override
    public String editProductEditRequestInfo(Long memberId, Long productId, UpdatePriceDto updatePriceDto){
        EditProductDto editProductDto = mypageRepository.getProductEditRequestInfo(memberId, productId);

        if (updatePriceDto.getUpdatePrice() > editProductDto.getPrice()) {
            logService.savedMemberLogWithTypeAndEtc(memberId, "상품 가격 수정 요청 실패", "/product/detail/" + productId);
            return "변경된 가격은 기존 가격보다 높을 수 없습니다.";
        }
        updatePrice(productId, updatePriceDto.getUpdatePrice(), memberId);
        return "가격 변경 요청이 완료되었습니다.";
    }

    // 수정신청 폼 제출
//    @Override
    @Transactional
    public void updatePrice(Long productId, Integer updatePrice, Long memberId){
        mypageRepository.updatePrice(productId, updatePrice);
        logService.savedMemberLogWithTypeAndEtc(memberId, "상품 가격 수정 요청", "/product/detail/" + productId);
        logger.info("memberId:{} 회원이 productId:{} 매물에 대해 가격 수정 요청", memberId, productId);
    }

    // 등록한 매물 삭제 신청
    @Override
    @Transactional
    public String productDeleteRequest(Long productId, Long memberId){
        mypageRepository.requestProductDelete(productId);
        logService.savedMemberLogWithTypeAndEtc(memberId, "상품 삭제 요청", "/product/detail/" + productId);
        logger.info("memberId:{} 회원이 productId:{} 매물에 대해 삭제 신청 요청", memberId, productId);
        return "삭제 신청이 완료되었습니다.";
    }

    // 프로필 수정 (GET요청 시 데이터 보여주기)
    @Override
    public EditProfileDto getProfileForEdit(Long memberId){
        Member member = memberRepository.findById(memberId).get();
        EditProfileDto editProdileDto = EditProfileDto.builder()
                .name(member.getName())
                .imageUrl(member.getProfileImage())
                .build();
        return editProdileDto;
    }

    // 프로필 수정 (PATCH요청 시 데이터 수정)
    @Override
    @Transactional
    public String editProfile(Long memberId, AmazonS3RequestDto amazonS3RequestDto) throws IOException {
        String email = memberRepository.findById(memberId).get().getEmail();
        amazonS3RequestDto.setEmail(email);
        String newProfileIamge = amazonS3Service.uploadProfile(amazonS3RequestDto);
        // TODO: multipartfile null 해결

        logService.savedMemberLogWithType(memberId, "프로필 이미지 수정");
        return newProfileIamge;
    }
}