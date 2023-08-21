package com.woochacha.backend.domain.mypage.service.impl;

import com.woochacha.backend.domain.car.detail.entity.CarName;
import com.woochacha.backend.domain.mypage.dto.ProductResponseDto;
import com.woochacha.backend.domain.mypage.repository.MypageRepository;
import com.woochacha.backend.domain.mypage.service.MypageService;
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

    // 페이지네이션 : 한 페이지당 5개, 게시글 작성일 최신순으로 정렬
    @Transactional
    public Page<ProductResponseDto> getRegisteredProductsByUserId(Long userId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<Object[]> productsPage = mypageRepository.getRegisteredProductsByUserId(userId, pageable);

        return productsPage.map(this::arrayToProductResponseDto);
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
                (Long) array[5],
                (LocalDateTime) array[6]
        );
    }
}

