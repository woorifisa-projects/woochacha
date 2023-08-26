package com.woochacha.backend.domain.mypage.service.impl;

import com.woochacha.backend.common.ModelMapping;
import com.woochacha.backend.domain.member.entity.Member;
import com.woochacha.backend.domain.member.repository.MemberRepository;
import com.woochacha.backend.domain.mypage.dto.ProductResponseDto;
import com.woochacha.backend.domain.mypage.dto.ProfileDto;
import com.woochacha.backend.domain.mypage.dto.SaleFormDto;
import com.woochacha.backend.domain.mypage.repository.MypageRepository;
import com.woochacha.backend.domain.mypage.service.MypageService;
import com.woochacha.backend.domain.sale.entity.Branch;
import com.woochacha.backend.domain.status.entity.CarStatus;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {

    private final MypageRepository mypageRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper = ModelMapping.getInstance();

    // JPQL로 조회한 결과 ProductResponseDto로 변환해서 전달
    private ProductResponseDto arrayToProductResponseDto(Object[] array) {
        return ProductResponseDto.builder()
                .title((String) array[0])
                .distance((Integer) array[1])
                .branch((String) array[2])
                .price((Integer) array[3])
                .imageUrl((String) array[4])
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
    public Page<ProductResponseDto> getRegisteredProductsByUserId(Long userId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<Object[]> productsPage = mypageRepository.getRegisteredProductsByUserId(userId, pageable);

        return productsPage.map(this::arrayToProductResponseDto);
    }

    // 판매 이력 조회
    public Page<ProductResponseDto> getSoldProductsByMemberId(Long userId, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<Object[]> productsPage = mypageRepository.getSoldProductsByMemberId(userId, pageable);

        return productsPage.map(this::arrayToProductResponseDto);
    }

    // 구매 이력 조회
    public Page<ProductResponseDto> getPurchaseProductsByMemberId(Long memberId, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<Object[]> productsPage = mypageRepository.getPurchaseProductsByMemberId(memberId, pageable);

        return productsPage.map(this::arrayToProductResponseDto);
    }

    // 프로필 조회
    public ProfileDto getProfileByMemberId(Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new RuntimeException("Member not found"));
        return modelMapper.map(member, ProfileDto.class);
    }

    // 판매 요청 폼 조회
    public Page<SaleFormDto> getSaleFormsByMemberId(Long memberId, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> saleFormsPage = mypageRepository.findAllByMemberId(memberId, pageable);
        Page<SaleFormDto> saleFormDtosPage = saleFormsPage.map(this::arrayToSaleFormDto);
        return saleFormDtosPage;
    }
}

