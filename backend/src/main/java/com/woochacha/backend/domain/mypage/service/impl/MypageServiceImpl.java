package com.woochacha.backend.domain.mypage.service.impl;

import com.woochacha.backend.common.ModelMapping;
import com.woochacha.backend.domain.member.entity.Member;
import com.woochacha.backend.domain.member.repository.MemberRepository;
import com.woochacha.backend.domain.mypage.dto.ProductResponseDto;
import com.woochacha.backend.domain.mypage.dto.ProfileDto;
import com.woochacha.backend.domain.mypage.dto.PurchaseReqeustListDto;
import com.woochacha.backend.domain.mypage.repository.MypageRepository;
import com.woochacha.backend.domain.mypage.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {

    private final MypageRepository mypageRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper = ModelMapping.getInstance();

    // JPQL로 조회한 결과 ProductResponseDto로 변환
    private ProductResponseDto arrayToProductResponseDto(Object[] array) {
        return ProductResponseDto.builder()
                .title((String) array[0])
                .distance((Integer) array[1])
                .branch((String) array[2])
                .price((Integer) array[3])
                .imageUrl((String) array[4])
                .build();
    }

    // JPQL로 조회한 결과 PurchaseReqeustListDto로 변환
    private PurchaseReqeustListDto arrayToPurchaseReqeustListDto(Object[] array){
        return PurchaseReqeustListDto.builder()
                .title((String) array[0])
                .price((Integer) array[1])
                .branch((String) array[2])
                .distance((Integer) array[3])
                .build();
    }

    // 등록한 매물 조회
    public Page<ProductResponseDto> getRegisteredProductsByMemberId(Long memberId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<Object[]> productsPage = mypageRepository.getRegisteredProductsByMemberId(memberId, pageable);

        return productsPage.map(this::arrayToProductResponseDto);
    }

    // 판매 이력 조회
    public Page<ProductResponseDto> getSoldProductsByMemberId(Long memberId, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<Object[]> productsPage = mypageRepository.getSoldProductsByMemberId(memberId, pageable);

        return productsPage.map(this::arrayToProductResponseDto);
    }

    // 구매 이력 조회
    public Page<ProductResponseDto> getPurchaseProductsByMemberId(Long memberId, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<Object[]> productsPage = mypageRepository.getPurchaseProductsByMemberId(memberId, pageable);

        return productsPage.map(this::arrayToProductResponseDto);
    }

    // 프로필 조회
    public ProfileDto getProfileByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        return modelMapper.map(member, ProfileDto.class);
    }

    // 구매 신청 내역 조회
    public Page<PurchaseReqeustListDto> getPurchaseRequestByMemberId(Long memberId, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<Object[]> purchaseRequestPage = mypageRepository.getPurchaseRequestByMemberId(memberId, pageable);

        return purchaseRequestPage.map(this::arrayToPurchaseReqeustListDto);
    }
}

