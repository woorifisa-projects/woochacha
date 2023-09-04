package com.woochacha.backend.domain.admin.service;

import com.woochacha.backend.domain.admin.dto.magageProduct.EditProductDto;
import com.woochacha.backend.domain.admin.dto.magageProduct.ManageProductFormDto;
import org.springframework.data.domain.Page;

public interface ManageProductFormService {

    Page<ManageProductFormDto> findDeleteEditForm(int pageNumber, int pageSize);

    void deleteProduct(Long productId);

    EditProductDto findEditForm(Long productId);
}
