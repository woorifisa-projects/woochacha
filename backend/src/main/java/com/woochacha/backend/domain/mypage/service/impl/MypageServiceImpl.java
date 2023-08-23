package com.woochacha.backend.domain.mypage.service.impl;

import com.woochacha.backend.domain.car.detail.entity.CarName;
import com.woochacha.backend.domain.mypage.dto.ProductResponseDto;
import com.woochacha.backend.domain.mypage.repository.MypageRepository;
import com.woochacha.backend.domain.mypage.service.MypageService;
import com.woochacha.backend.domain.sale.entity.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class MypageServiceImpl implements MypageService {

    private MypageRepository mypageRepository;

    public MypageServiceImpl(MypageRepository mypageRepository) {
        this.mypageRepository = mypageRepository;
    }

    // JPQL로 조회한 결과 ProductResponseDto로 변환해서 전달
    private ProductResponseDto arrayToProductResponseDto(Object[] array) {
        CarName carName = (CarName) array[0];
        return new ProductResponseDto(
                carName.getName(),
                (String) array[1],
                (Integer) array[2],
                (Short) array[3],
                (Integer) array[4],
                (Branch) array[5],
                (LocalDateTime) array[6]
        );
    }

    // 등록한 매물 조회 (최신 등록 순)
    @Transactional
    public Page<ProductResponseDto> getRegisteredProductsByUserId(Long userId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<Object[]> productsPage = mypageRepository.getRegisteredProductsByUserId(userId, pageable);

        return productsPage.map(this::arrayToProductResponseDto);
    }

    // 판매 이력 조회 (최신 판매 순)
    @Transactional
    public Page<ProductResponseDto> getSoldProductsByMemberId(Long userId, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<Object[]> productsPage = mypageRepository.getSoldProductsByMemberId(userId, pageable);

        return productsPage.map(this::arrayToProductResponseDto);
    }
}

