package com.woochacha.backend.domain.admin.controller;

import com.woochacha.backend.domain.admin.dto.ManageProductFormDto;
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
    public ResponseEntity<Page<ManageProductFormDto>> getrequestForm(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "5") int size) {
        Page<ManageProductFormDto> productFormsPage = manageProductFormService.findDeleteEditForm(page, size);
        return ResponseEntity.ok(productFormsPage);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<String> permitDeleteRequest(@PathVariable("productId") Long productId){
        manageProductFormService.deleteProduct(productId);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }
}
