package com.woochacha.backend.domain.admin.service.impl;

import com.woochacha.backend.domain.admin.dto.magageProduct.EditProductDto;
import com.woochacha.backend.domain.admin.dto.magageProduct.ManageProductFormDto;
import com.woochacha.backend.domain.admin.repository.ManageProductFormRepository;
import com.woochacha.backend.domain.admin.service.ManageProductFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManageProductFormServiceImpl implements ManageProductFormService {

    private final ManageProductFormRepository manageProductFormRepository;

    private ManageProductFormDto arrayToManageProductFormDto(Object[] array){
        try {
            String status;
            if (array[4].toString().equals("6")) {
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
        }catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    // 매물 관리 리스트 조회
    @Override
    public Page<ManageProductFormDto> findDeleteEditForm(int pageNumber, int pageSize){
        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Page<Object[]> deleteEditForms = manageProductFormRepository.getDeleteEditForm(pageable);
            return deleteEditForms.map(this::arrayToManageProductFormDto);
        } catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    // 매물 삭제 처리
    @Transactional
    @Override
    public void deleteProduct(Long productId){
        try {
            manageProductFormRepository.deleteProduct(productId);
        }catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    // 매물 수정 처리를 위한 팝업창 데이터 가져오기
    @Override
    public EditProductDto findEditForm(Long productId){
        try {
            return manageProductFormRepository.getEditForm(productId);
        } catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    // 매물 수정 반려
    @Transactional
    @Override
    public void denyEditRequest(Long productId){
        try {
            manageProductFormRepository.denyEditRequest(productId);
        }catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    // 매물 수정 승인
    @Transactional
    @Override
    public void permitEditRequest(Long productId){
        try {
            manageProductFormRepository.permitEditRequest(productId);
        } catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}
