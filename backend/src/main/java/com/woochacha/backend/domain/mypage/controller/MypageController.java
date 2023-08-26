package com.woochacha.backend.domain.mypage.controller;

import com.woochacha.backend.domain.mypage.dto.ProductResponseDto;
import com.woochacha.backend.domain.mypage.dto.ProfileDto;
import com.woochacha.backend.domain.mypage.dto.PurchaseReqeustListDto;
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
    마이페이지 - 등록한 매물 조회
    페이지네이션 : 한 페이지당 5개
    */
    @GetMapping("/registered/{memberId}")
    private ResponseEntity<Page<ProductResponseDto>> registeredProduct(@PathVariable Long memberId,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "5") int size) {
        Page<ProductResponseDto> productsPage = mypageService.getRegisteredProductsByMemberId(memberId, page, size);
        return ResponseEntity.ok(productsPage);
    }

    /*
     마이페이지 - 판매 이력 조회
     페이지네이션 : 한 페이지당 5개
     */
    @GetMapping("/sale/{memberId}")
    private ResponseEntity<Page<ProductResponseDto>> soldProduct(@PathVariable Long memberId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "5") int size){
        Page<ProductResponseDto> productsPage = mypageService.getSoldProductsByMemberId(memberId, page, size);
        return ResponseEntity.ok(productsPage);
    }

    /*
     마이페이지 구매 이력 조회
     페이지네이션 : 한 페이지당 5개
     */
    @GetMapping("/purchase/{memberId}")
    private ResponseEntity<Page<ProductResponseDto>> purchaseProduct(@PathVariable Long memberId,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "5") int size) {
        Page<ProductResponseDto> productsPage = mypageService.getPurchaseProductsByMemberId(memberId, page, size);
        return ResponseEntity.ok(productsPage);
    }


//  마이페이지 프로필 조회
    @GetMapping("/{memberId}")
    private ResponseEntity<ProfileDto> mypage(@PathVariable Long memberId){
        ProfileDto profileDto = mypageService.getProfileByMemberId(memberId);
        return ResponseEntity.ok(profileDto);
    }

//  마이페이지 구매 요청 내역 조회
    @GetMapping("/purchase-request/{memberId}")
    private ResponseEntity<Page<PurchaseReqeustListDto>> purchaseRequestList(@PathVariable Long memberId,
                                                                             @RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "5") int size){
        Page<PurchaseReqeustListDto> purchaseRequestPage = mypageService.getPurchaseRequestByMemberId(memberId, page, size);
        return ResponseEntity.ok(purchaseRequestPage);
    }
}
