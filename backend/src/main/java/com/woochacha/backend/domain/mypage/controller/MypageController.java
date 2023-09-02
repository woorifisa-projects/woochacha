package com.woochacha.backend.domain.mypage.controller;

import com.woochacha.backend.domain.AmazonS3.dto.AmazonS3RequestDto;
import com.woochacha.backend.domain.jwt.JwtAuthenticationFilter;
import com.woochacha.backend.domain.mypage.dto.*;
import com.woochacha.backend.domain.mypage.service.impl.MypageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

    private final MypageServiceImpl mypageService;
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

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
            return ResponseEntity.badRequest().body("변경된 가격은 기존 가격보다 높을 수 없습니다.");
        }
        mypageService.updatePrice(productId, updatePriceDto.getUpdatePrice());
        return ResponseEntity.ok("가격 변경 요청이 완료되었습니다.");
    }

    // 프로필 이미지 수정 (데이터 조회 Get)
    @GetMapping("/profile/edit/{memberId}")
    private ResponseEntity<EditProdileDto> getProfileForEdit(@PathVariable("memberId") Long memberId){
        EditProdileDto editProdileDto = mypageService.getProfileForEdit(memberId);
        return ResponseEntity.ok(editProdileDto);
    }

    // 프로필 이미지 수정 (데이터 수정 Patch)
    @PatchMapping("/profile/edit/{memberId}")
    private ResponseEntity<String> editProfile(@PathVariable("memberId") Long memberId, @ModelAttribute AmazonS3RequestDto amazonS3RequestDto ) throws IOException {
        String newProfileImage = mypageService.editProfile(memberId, amazonS3RequestDto);
        return ResponseEntity.ok("프로필 수정이 완료되었습니다.");
    }
}
