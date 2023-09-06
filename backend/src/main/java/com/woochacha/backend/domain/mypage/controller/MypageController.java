package com.woochacha.backend.domain.mypage.controller;

import com.woochacha.backend.domain.AmazonS3.dto.AmazonS3RequestDto;
import com.woochacha.backend.domain.mypage.dto.*;
import com.woochacha.backend.domain.mypage.service.impl.MypageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;

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
    private ResponseEntity<Page<ProductResponseDto>> registeredProductList(@PathVariable Long memberId,
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
    private ResponseEntity<Page<ProductResponseDto>> soldProductList(@PathVariable Long memberId,
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
    private ResponseEntity<Page<ProductResponseDto>> purchasedProductList(@PathVariable Long memberId,
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

    // 마이페이지 판매 신청폼 조회
    @GetMapping("/sale-request/{memberId}")
    public ResponseEntity<Page<SaleFormDto>> retrievePosts(@PathVariable Long memberId,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5") int size) {
        Page<SaleFormDto> saleFormsPage = mypageService.getSaleFormsByMemberId(memberId, page, size);
        return ResponseEntity.ok(saleFormsPage);
    }
      
    // 마이페이지 구매 신청폼 조회
    @GetMapping("/purchase-request/{memberId}")
    private ResponseEntity<Page<PurchaseReqeustListDto>> purchaseRequestList(@PathVariable Long memberId,
                                                                             @RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "5") int size){
        Page<PurchaseReqeustListDto> purchaseRequestPage = mypageService.getPurchaseRequestByMemberId(memberId, page, size);
        return ResponseEntity.ok(purchaseRequestPage);
    }

    // 상품 수정 신청폼 조회
    @GetMapping("/registered/edit")
    private ResponseEntity<EditProductDto> getProductEditForm(@RequestParam("memberId") Long memberId, @RequestParam("productId") Long productId){
        EditProductDto editProductDto = mypageService.getProductEditRequestInfo(memberId, productId);
        return ResponseEntity.ok(editProductDto);
    }

    // 상품 수정 신청폼 제출
    @PatchMapping("/registered/edit")
    private ResponseEntity<String> editProductEditForm(@RequestParam("memberId") Long memberId, @RequestParam("productId") Long productId,
                                                        @RequestBody @Valid UpdatePriceDto updatePriceDto){
        EditProductDto editProductDto = mypageService.getProductEditRequestInfo(memberId, productId);
        if (updatePriceDto.getUpdatePrice() > editProductDto.getPrice()) {
            // TODO: 리팩토링 후, 이 부분 코드가 서비스 구현체로 들어가면 그때 "가격 변경 요청 실패" 로그 추가
            return ResponseEntity.badRequest().body("변경된 가격은 기존 가격보다 높을 수 없습니다.");
        }
        mypageService.updatePrice(productId, updatePriceDto.getUpdatePrice(), memberId);
        return ResponseEntity.ok("가격 변경 요청이 완료되었습니다.");
    }

    // 등록한 매물 삭제 요청
    @PatchMapping("/registered/delete/{productId}")
    private ResponseEntity<String> productDeleteRequest(@PathVariable Long productId,
                                                        @RequestHeader(value = "memberId") Long memberId) {
        mypageService.productDeleteRequest(productId, memberId);
        return ResponseEntity.ok("삭제 신청이 완료되었습니다.");
    }


    // 프로필 이미지 수정 (데이터 조회 Get)
    @GetMapping("/profile/edit/{memberId}")
    private ResponseEntity<EditProfileDto> getProfileForEdit(@PathVariable("memberId") Long memberId){
        EditProfileDto editProfileDto = mypageService.getProfileForEdit(memberId);
        return ResponseEntity.ok(editProfileDto);
    }

    // 프로필 이미지 수정 (데이터 수정 Patch)
    @PatchMapping("/profile/edit/{memberId}")
    private ResponseEntity<String> editProfile(@PathVariable("memberId") Long memberId, @ModelAttribute AmazonS3RequestDto amazonS3RequestDto ) throws IOException {
        String newProfileImage = mypageService.editProfile(memberId, amazonS3RequestDto);
        return ResponseEntity.ok("프로필 수정이 완료되었습니다.");
    }
}
