package com.woochacha.backend.domain.mypage.controller;

import com.woochacha.backend.domain.mypage.dto.ProductResponseDto;
import com.woochacha.backend.domain.mypage.dto.ProfileDto;
import com.woochacha.backend.domain.mypage.service.impl.MypageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

    private final MypageServiceImpl mypageService;

    /*
    [마이페이지 - 등록한 매물 조회]
    반환 데이터 : "carName", "imageUrl", "price", "year", "distance", "branch", "createdAt"
    페이지네이션 : 한 페이지당 5개, 게시글 작성일 최신순으로 정렬
    */
    @GetMapping("/registered/{memberId}")
    private ResponseEntity<Page<ProductResponseDto>> registeredProduct(@PathVariable Long memberId,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "5") int size) {
        Page<ProductResponseDto> productsPage = mypageService.getRegisteredProductsByUserId(memberId, page, size);
        return ResponseEntity.ok(productsPage);
    }

//    /*
//     [마이페이지 - 판매 이력 조회]
//     반환 데이터 : "carName", "imageUrl", "price", "year", "distance", "branch", "createdAt"
//     페이지네이션 : 한 페이지당 5개, 게시글 작성일 최신순으로 정렬
//     */
    @GetMapping("/sale/{memberId}")
    private ResponseEntity<Page<ProductResponseDto>> soldProduct(@PathVariable Long memberId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "5") int size){
        Page<ProductResponseDto> productsPage = mypageService.getSoldProductsByMemberId(memberId, page, size);
        return ResponseEntity.ok(productsPage);
    }

//    /*
//     [마이페이지 - 구매 이력 조회]
//     반환 데이터 : "carName", "imageUrl", "price", "year", "distance", "branch", "createdAt"
//     페이지네이션 : 한 페이지당 5개, 게시글 작성일 최신순으로 정렬
//     */
    @GetMapping("/purchase/{memberId}")
    private ResponseEntity<Page<ProductResponseDto>> purchaseProduct(@PathVariable Long memberId,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "5") int size) {
        Page<ProductResponseDto> productsPage = mypageService.getPurchaseProductsByMemberId(memberId, page, size);
        return ResponseEntity.ok(productsPage);
    }

    /*
    [마이페이지 프로필 조회]
    반환 데이터 : "profileImage", "name", "phone", "email"
     */
    @GetMapping("/{memberId}")
    private ResponseEntity<ProfileDto> mypage(@PathVariable Long memberId){
        ProfileDto profileDto = mypageService.getProfileByMemberId(memberId);
        return ResponseEntity.ok(profileDto);
    }
}
