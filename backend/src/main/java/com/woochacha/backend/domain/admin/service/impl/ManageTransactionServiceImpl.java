package com.woochacha.backend.domain.admin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseDateRequestDto;
import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseMemberInfoResponseDto;
import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseFormListResponseDto;
import com.woochacha.backend.domain.admin.dto.sendMessage.MessageDto;
import com.woochacha.backend.domain.admin.dto.sendMessage.SmsRequestDto;
import com.woochacha.backend.domain.admin.dto.sendMessage.SmsResponseDto;
import com.woochacha.backend.domain.admin.service.ManageTransactionService;
import com.woochacha.backend.domain.product.repository.ProductRepository;
import com.woochacha.backend.domain.purchase.entity.PurchaseForm;
import com.woochacha.backend.domain.purchase.repository.PurchaseFormRepository;
import com.woochacha.backend.domain.transaction.entity.Transaction;
import com.woochacha.backend.domain.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManageTransactionServiceImpl implements ManageTransactionService {

    @Value("${naver-cloud-sms.accessKey}")
    private String accessKey;

    @Value("${naver-cloud-sms.secretKey}")
    private String secretKey;

    @Value("${naver-cloud-sms.serviceId}")
    private String serviceId;

    @Value("${naver-cloud-sms.senderPhone}")
    private String phone;

    private final PurchaseFormRepository purchaseFormRepository;
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
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
        return purchaseFormRepository.findPurchaseMemberInfo(purchaseId);
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
        sendSms(sellerMessageDto);
        logger.info("carNum:{} 차량에 대한 판매날짜 SMS 전송 완료", carNum);

        MessageDto buyerMessageDto = MessageDto.builder().to(purchaseMemberInfoResponseDto.getBuyerPhone()).content("<구매 날짜 알림>" + "\n"
                + carNum +  " 차량 구매 날짜가 결정되었습니다." + "\n"
                + "거래소 : " + branch + " 우차차 차고지" + "\n"
                + "날짜 : " + meetingDate + "\n"
                + "판매자 : " + purchaseMemberInfoResponseDto.getSellerName() + "\n"
                + "상담 문의 : " + "우차차 고객센터( "  + phone + " )").build();
        sendSms(buyerMessageDto);
        logger.info("carNum:{} 차량에 대한 구매날짜 SMS 전송 완료", carNum);
    }
    private void sendSms(MessageDto messageDto) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException, URISyntaxException {
        String time = Long.toString(System.currentTimeMillis());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time);
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", getSignature(time)); // signature 서명

        List<MessageDto> messages = new ArrayList<>();
        messages.add(messageDto);
        // api 요청 양식
        SmsRequestDto request = SmsRequestDto.builder()
                .type("LMS")
                .contentType("COMM")
                .countryCode("82")
                .from(phone)
                .content("문자 발송")
                .messages(messages)
                .build();

        //request를 sens에 보내기 위한 body를 json형태로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(request);
        // body와 header을 한번에 보내는 코드
        HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        //restTemplate를 통해 외부 api와 통신
        restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/messages"), httpBody, SmsResponseDto.class);
    }
    private String getSignature(String time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/"+ this.serviceId+"/messages";
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(time)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));

        return Base64.encodeBase64String(rawHmac);
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
