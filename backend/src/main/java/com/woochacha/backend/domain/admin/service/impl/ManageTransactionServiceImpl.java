package com.woochacha.backend.domain.admin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woochacha.backend.common.DataMasking;
import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseDateRequestDto;
import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseMemberInfoResponseDto;
import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseFormListResponseDto;
import com.woochacha.backend.domain.admin.service.ManageTransactionService;
import com.woochacha.backend.domain.product.repository.ProductRepository;
import com.woochacha.backend.domain.purchase.entity.PurchaseForm;
import com.woochacha.backend.domain.purchase.repository.PurchaseFormRepository;
import com.woochacha.backend.domain.sendSMS.SendSmsService;
import com.woochacha.backend.domain.sendSMS.sendMessage.MessageDto;
import com.woochacha.backend.domain.transaction.entity.Transaction;
import com.woochacha.backend.domain.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManageTransactionServiceImpl implements ManageTransactionService {

    @Value("${naver-cloud-sms.senderPhone}")
    private String phone;

    private final PurchaseFormRepository purchaseFormRepository;
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final SendSmsService sendSmsService;
    private final DataMasking dataMasking;
    private static Logger logger = LoggerFactory.getLogger(ManageTransactionServiceImpl.class);

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
                    .purchaseId((Long) data[5])
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
        PurchaseMemberInfoResponseDto purchaseMemberInfoResponseDto = purchaseFormRepository.findPurchaseMemberInfo(purchaseId);
        purchaseMemberInfoResponseDto.setSellerPhone(dataMasking.decoding(purchaseMemberInfoResponseDto.getSellerPhone()));
        purchaseMemberInfoResponseDto.setBuyerPhone(dataMasking.decoding(purchaseMemberInfoResponseDto.getBuyerPhone()));
        return purchaseMemberInfoResponseDto;
    }

    @Override
    @Transactional
    public String matchPurchaseDate(Long purchaseId, PurchaseDateRequestDto purchaseDateRequestDto) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        purchaseFormRepository.updatePurchaseDateANDStatus(purchaseId, purchaseDateRequestDto.getMeetingDate());
        setMessage(purchaseFormRepository.getCarNumByPurchaseId(purchaseId), getPurchaseMemberInfo(purchaseId), String.valueOf(purchaseDateRequestDto.getMeetingDate()), purchaseFormRepository.getBranchByPurchaseId(purchaseId).name() );
        return "날짜 매칭에 성공하였습니다.";
    }

    private void setMessage(String carNum, PurchaseMemberInfoResponseDto purchaseMemberInfoResponseDto, String meetingDate, String branch) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        MessageDto sellerMessageDto = MessageDto.builder().to(purchaseMemberInfoResponseDto.getSellerPhone()).content("<판매 날짜 알림>" + "\n"
                + carNum +  " 차량 판매 날짜가 결정되었습니다." + "\n"
                + "거래소 : " + branch + " 우차차 차고지" + "\n"
                + "날짜 : " + meetingDate + "\n"
                + "구매 희망자 : " + purchaseMemberInfoResponseDto.getBuyerName() + "\n"
                + "상담 문의 : " + "우차차 고객센터( " + phone + " )").build();
        sendSmsService.sendSms(sellerMessageDto);
        logger.info("carNum:{} 차량에 대한 판매날짜 SMS 전송 완료", carNum);

        MessageDto buyerMessageDto = MessageDto.builder().to(purchaseMemberInfoResponseDto.getBuyerPhone()).content("<구매 날짜 알림>" + "\n"
                + carNum +  " 차량 구매 날짜가 결정되었습니다." + "\n"
                + "거래소 : " + branch + " 우차차 차고지" + "\n"
                + "날짜 : " + meetingDate + "\n"
                + "판매자 : " + purchaseMemberInfoResponseDto.getSellerName() + "\n"
                + "상담 문의 : " + "우차차 고객센터( "  + phone + " )").build();
        sendSmsService.sendSms(buyerMessageDto);
        logger.info("carNum:{} 차량에 대한 구매날짜 SMS 전송 완료", carNum);
    }

    @Override
    @Transactional
    public String insertNewTransaction(Long purchaseId){
        Long saleFormId = purchaseFormRepository.getSaleFormId(purchaseId);
        transactionRepository.insertTransaction(saleFormId,purchaseId);
        PurchaseForm purchaseForm = purchaseFormRepository.findById(purchaseId).orElseThrow(() -> new RuntimeException("SaleForm not found"));
        Long productId = purchaseForm.getProduct().getId();
        productRepository.updateProductSuccessStatus(productId);
        logger.info("purchasID:{}매물에 대한 거래 성사", purchaseId);
        return "거래가 성사되었습니다.";
    }
}
