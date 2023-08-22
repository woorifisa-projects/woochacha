package com.woochacha.backend.domain.mypage.controller;

import com.woochacha.backend.domain.mypage.dto.ProductResponseDto;
import com.woochacha.backend.domain.mypage.service.impl.MypageServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mypage")
public class MypageController {

    private final MypageServiceImpl mypageService;

    public MypageController(MypageServiceImpl mypageService) {
        this.mypageService = mypageService;
    }

    /*
     [마이페이지 - 등록한 매물 조회]
     반환 데이터 : "carName", "imageUrl", "price", "year", "distance", "branchId", "createdAt"
     */
    @GetMapping("/registered/{user_id}")
    public ResponseEntity<Page<ProductResponseDto>> registeredProduct(@PathVariable Long user_id,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "5") int size) {
        Page<ProductResponseDto> productsPage = mypageService.getRegisteredProductsByUserId(user_id, page, size);
        return ResponseEntity.ok(productsPage);
    }
}
