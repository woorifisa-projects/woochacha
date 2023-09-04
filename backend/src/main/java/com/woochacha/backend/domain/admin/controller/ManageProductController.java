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

    @GetMapping
    public ResponseEntity<Page<ManageProductFormDto>> getRequestForm(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "5") int size) {
        Page<ManageProductFormDto> productFormsPage = manageProductFormService.findDeleteEditForm(page, size);
        return ResponseEntity.ok(productFormsPage);
    }

    @PatchMapping("/delete/{productId}")
    public ResponseEntity<String> permitDeleteRequest(@PathVariable("productId") Long productId){
        manageProductFormService.deleteProduct(productId);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }

    @GetMapping("/edit/{productId}")
    public ResponseEntity<EditProductDto> getEditForm(@PathVariable("productId") Long productId){
        EditProductDto editProductDto = manageProductFormService.findEditForm(productId);
        return ResponseEntity.ok(editProductDto);
    }
}
