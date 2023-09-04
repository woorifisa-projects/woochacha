package com.woochacha.backend.domain.admin.service.impl;

import com.woochacha.backend.domain.admin.dto.magageProduct.EditProductDto;
import com.woochacha.backend.domain.admin.dto.magageProduct.ManageProductFormDto;
import com.woochacha.backend.domain.admin.repository.ManageProductFormRepository;
import com.woochacha.backend.domain.admin.service.ManageProductFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManageProductFormServiceImpl implements ManageProductFormService {

    private final ManageProductFormRepository manageProductFormRepository;

    private ManageProductFormDto arrayToManageProductFormDto(Object[] array){
        String status;
        if (array[4].toString().equals("6")){
            status = "삭제";
        } else {
            status = "수정";
        }
        return ManageProductFormDto.builder()
                .productId((Long) array[0])
                .title((String) array[1])
                .sellerName((String) array[2])
                .sellerEmail((String) array[3])
                .manageType(status)
                .build();
    }

    @Override
    public Page<ManageProductFormDto> findDeleteEditForm(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> deleteEditForms = manageProductFormRepository.getDeleteEditForm(pageable);
        return deleteEditForms.map(this::arrayToManageProductFormDto);
    }

    @Transactional
    @Override
    public void deleteProduct(Long productId){
        manageProductFormRepository.deleteProduct(productId);
    }

    @Override
    public EditProductDto findEditForm(Long productId){
        EditProductDto editProductDto = manageProductFormRepository.getEditForm(productId);
        return editProductDto;
    }
}
