package com.woochacha.backend.domain.admin.controller;

import com.woochacha.backend.domain.admin.dto.magageProduct.EditProductDto;
import com.woochacha.backend.domain.admin.dto.magageProduct.ManageProductFormDto;
import com.woochacha.backend.domain.admin.service.impl.ManageProductFormServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/product")
public class ManageProductController {

    private final ManageProductFormServiceImpl manageProductFormService;

    // 매물 관리 리스트 조회를 위한 GetMapping
    @GetMapping
    public ResponseEntity<Page<ManageProductFormDto>> getRequestForm(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "5") int size) {
        Page<ManageProductFormDto> productFormsPage = manageProductFormService.findDeleteEditForm(page, size);
        return ResponseEntity.ok(productFormsPage);
    }

    // 매물 삭제 신청 처리를 위한 PatchMapping
    @PatchMapping("/delete/{productId}")
    public ResponseEntity<String> permitDeleteRequest(@PathVariable("productId") Long productId){
        manageProductFormService.deleteProduct(productId);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }

    // 매물 가격 수정 처리시 팝업창에 나타나는 정보 조회를 위한 GetMapping
    @GetMapping("/edit/{productId}")
    public ResponseEntity<EditProductDto> getEditForm(@PathVariable("productId") Long productId){
        EditProductDto editProductDto = manageProductFormService.findEditForm(productId);
        return ResponseEntity.ok(editProductDto);
    }

    // 매물 가격 수정 반려를 위한 PatchMapping (반려 버튼 클릭시 "삭제신청완료"였던 product 객체의 status 값을 "판매중"으로 변경)
    @PatchMapping("/edit/deny/{productId}")
    public ResponseEntity<String> denyEditRequest(@PathVariable("productId") Long productId){
        manageProductFormService.denyEditRequest(productId);
        return ResponseEntity.ok("가격 변경이 반려되었습니다.");
    }

    // 매물 가격 수정 승인을 위한 PatchMapping
    @PatchMapping("/edit/{productId}")
    public ResponseEntity<String> permitEditRequest(@PathVariable("productId") Long productId){
        manageProductFormService.permitEditRequest(productId);
        return ResponseEntity.ok("가격 변경이 완료되었습니다.");
    }
}
