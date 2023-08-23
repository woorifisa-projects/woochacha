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
    반환 데이터 : "carName", "imageUrl", "price", "year", "distance", "branch", "createdAt"
    페이지네이션 : 한 페이지당 5개, 게시글 작성일 최신순으로 정렬
    */
    @GetMapping("/registered/{user_id}")
    public ResponseEntity<Page<ProductResponseDto>> registeredProduct(@PathVariable Long user_id,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "5") int size) {
        Page<ProductResponseDto> productsPage = mypageService.getRegisteredProductsByUserId(user_id, page, size);
        return ResponseEntity.ok(productsPage);
    }

    /*
     [마이페이지 - 판매 이력 조회]
     반환 데이터 : "carName", "imageUrl", "price", "year", "distance", "branch", "createdAt"
     페이지네이션 : 한 페이지당 5개, 게시글 작성일 최신순으로 정렬
     */
    @GetMapping("/sale/{user_id}")
    public ResponseEntity<Page<ProductResponseDto>> soldProduct(@PathVariable Long user_id,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "5") int size){
        Page<ProductResponseDto> productsPage = mypageService.getSoldProductsByMemberId(user_id, page, size);
        return ResponseEntity.ok(productsPage);
    }

    /*
     [마이페이지 - 구매 이력 조회]
     반환 데이터 : "carName", "imageUrl", "price", "year", "distance", "branch", "createdAt"
     페이지네이션 : 한 페이지당 5개, 게시글 작성일 최신순으로 정렬
     */
    @GetMapping("/purchase/{user_id}")
    public ResponseEntity<Page<ProductResponseDto>> purchaseProduct(@PathVariable Long user_id,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "5") int size) {
        Page<ProductResponseDto> productsPage = mypageService.getPurchaseProductsByMemberId(user_id, page, size);
        return ResponseEntity.ok(productsPage);
    }
}
