package com.woochacha.backend.domain.admin.service.impl;

import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseMemberInfoResponseDto;
import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseFormListResponseDto;
import com.woochacha.backend.domain.admin.service.ManageTransactionService;
import com.woochacha.backend.domain.purchase.repository.PurchaseFormRepository;
import com.woochacha.backend.domain.transaction.entity.Transaction;
import com.woochacha.backend.domain.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManageTransactionServiceImpl implements ManageTransactionService {
    private final PurchaseFormRepository purchaseFormRepository;
    private final TransactionRepository transactionRepository;
    @Override
    public Page<PurchaseFormListResponseDto> getAllPurchaseFormInfo(Pageable pageable) {
        Page<Object[]> purchaseFormDataList = purchaseFormRepository.findAllPurchaseFormInfo(pageable);
        List<PurchaseFormListResponseDto> purchaseFormDtoList = new ArrayList<>();

        for (Object[] data : purchaseFormDataList.getContent()) {
            Transaction transaction = transactionRepository.findBySaleIdAndPurchaseId((Long) data[6], (Long) data[5]);
            int transactionStatus = 0;
            if(transaction != null){
                transactionStatus = 1;
            }
            PurchaseFormListResponseDto dto = PurchaseFormListResponseDto.builder()
                    .productId((Long) data[0])
                    .productUrl("http://localhost:8080/products/detail/" + data[0])
                    .carNum((String) data[1])
                    .buyerName((String) data[2])
                    .sellerName((String) data[3])
                    .purchaseStatus((int) data[4])
                    .transactionStatus(transactionStatus)
                    .build();
            purchaseFormDtoList.add(dto);
        }
        return new PageImpl<>(purchaseFormDtoList,pageable,purchaseFormDataList.getTotalElements());
    }

    @Override
    public PurchaseMemberInfoResponseDto getPurchaseMemberInfo(Long purchaseId){
        return purchaseFormRepository.findPurchaseMemberInfo(purchaseId);
    }
}
