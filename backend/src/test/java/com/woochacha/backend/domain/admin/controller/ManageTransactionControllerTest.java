package com.woochacha.backend.domain.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.querydsl.core.QueryResults;
import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseDateRequestDto;
import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseFormListResponseDto;
import com.woochacha.backend.domain.admin.dto.manageMember.PurchaseMemberInfoResponseDto;
import com.woochacha.backend.domain.admin.service.ManageTransactionService;
import com.woochacha.backend.domain.common.CommonTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ManageTransactionControllerTest extends CommonTest {

    @InjectMocks
    private ManageTransactionController manageTransactionController;

    @Mock
    private ManageTransactionService manageTransactionService;

    @BeforeEach
    public void init(RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.standaloneSetup(manageTransactionController)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // PurchaseDateRequestDto의 LocalDate 객체를 직렬화할 때 사용
    }
    @Test
    @DisplayName("구매 요청 목록을 조회한다.")
    void getAllPurchaseList() throws Exception {
        PurchaseFormListResponseDto purchaseFormListResponseDto = PurchaseFormListResponseDto.builder()
                .productId(1L)
                .purchaseId(2L)
                .carNum("22가 1111")
                .buyerName("홍길동")
                .sellerName("김철수")
                .purchaseStatus(0)
                .transactionStatus(1)
                .build();
        List<PurchaseFormListResponseDto> result = new ArrayList<>();
        result.add(purchaseFormListResponseDto);

        QueryResults<PurchaseFormListResponseDto> purchaseFormListResponseDtoList = new QueryResults<>(result, 5L, 0L, 10L);

        int page = 0, size = 5;

        Pageable pageable = PageRequest.of(page,size);
        when(manageTransactionService.getAllPurchaseFormInfo(pageable)).thenReturn(new PageImpl<>(purchaseFormListResponseDtoList.getResults()));

        mockMvc.perform(get("/admin/purchase")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("admin/purchase/get-form",
                        requestParameters(
                                parameterWithName("page").description("페이지 오프셋"),
                                parameterWithName("size").description("페이지당 보여줄 매물 개수")
                        ),
                        responseFields(
                                fieldWithPath("content[].productId").description("판매글 아이디"),
                                fieldWithPath("content[].purchaseId").description("구매 요청 아이디"),
                                fieldWithPath("content[].carNum").description("차량 번호"),
                                fieldWithPath("content[].buyerName").description("구매자 이름"),
                                fieldWithPath("content[].sellerName").description("판매자 이름"),
                                fieldWithPath("content[].purchaseStatus").description("구매 요청 상태"),
                                fieldWithPath("content[].transactionStatus").description("거래 상태"),

                                fieldWithPath("pageable").description("페이징 정보"),
                                fieldWithPath("last").description("마지막 페이지 여부"),
                                fieldWithPath("totalPages").description("총 페이지 수"),
                                fieldWithPath("totalElements").description("총 요소 수"),
                                fieldWithPath("size").description("페이지 크기"),
                                fieldWithPath("number").description("현재 페이지 번호"),
                                fieldWithPath("sort.empty").description("정렬 여부 (비어 있음)"),
                                fieldWithPath("sort.sorted").description("정렬 여부 (정렬됨)"),
                                fieldWithPath("sort.unsorted").description("정렬 여부 (정렬되지 않음)"),

                                fieldWithPath("numberOfElements").description("현재 페이지의 요소 수"),
                                fieldWithPath("first").description("첫 번째 페이지 여부"),
                                fieldWithPath("empty").description("결과가 비어 있는지 여부")
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].productId").value(purchaseFormListResponseDto.getProductId()))
                .andExpect(jsonPath("$.content[0].purchaseId").value(purchaseFormListResponseDto.getPurchaseId()))
                .andExpect(jsonPath("$.content[0].carNum").value(purchaseFormListResponseDto.getCarNum()))
                .andExpect(jsonPath("$.content[0].buyerName").value(purchaseFormListResponseDto.getBuyerName()))
                .andExpect(jsonPath("$.content[0].sellerName").value(purchaseFormListResponseDto.getSellerName()))
                .andExpect(jsonPath("$.content[0].purchaseStatus").value(purchaseFormListResponseDto.getPurchaseStatus()))
                .andExpect(jsonPath("$.content[0].transactionStatus").value(purchaseFormListResponseDto.getTransactionStatus()))
                .andReturn();
    }

    @Test
    @DisplayName("구매 신청폼의 판매자, 구매자 정보를 조회한다.")
    void matchPurchaseInfo() throws Exception {
        PurchaseMemberInfoResponseDto purchaseMemberInfoResponseDto = PurchaseMemberInfoResponseDto.builder()
                .sellerName("홍길동")
                .sellerPhone("01055556666")
                .buyerName("김철수")
                .buyerPhone("01077778888")
                .build();

        Long purchaseId = 1L;

        when(manageTransactionService.getPurchaseMemberInfo(purchaseId)).thenReturn(purchaseMemberInfoResponseDto);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/admin/purchase/{purchaseId}", purchaseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("admin/purchase/get-user",
                        pathParameters(
                                parameterWithName("purchaseId").description("구매 요청 아이디")
                        ),
                        responseFields(
                                fieldWithPath("sellerName").description("판매자 이름"),
                                fieldWithPath("sellerPhone").description("판매자 핸드폰 번호"),
                                fieldWithPath("buyerName").description("구매자 이름"),
                                fieldWithPath("buyerPhone").description("구매자 핸드폰 번호")
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sellerName").value(purchaseMemberInfoResponseDto.getSellerName()))
                .andExpect(jsonPath("$.sellerPhone").value(purchaseMemberInfoResponseDto.getSellerPhone()))
                .andExpect(jsonPath("$.buyerName").value(purchaseMemberInfoResponseDto.getBuyerName()))
                .andExpect(jsonPath("$.buyerPhone").value(purchaseMemberInfoResponseDto.getBuyerPhone()))
                .andReturn();
    }

    @Test
    @DisplayName("구매 신청폼에서 선택한 희망 거래일을 저장한다.")
    void matchPurchaseDate() throws Exception {
        PurchaseDateRequestDto purchaseDateRequestDto = new PurchaseDateRequestDto(LocalDate.now());
        Long purchaseId = 1L;
        String returnMessage = "날짜 매칭에 성공하였습니다.";

        when(manageTransactionService.matchPurchaseDate(anyLong(), any(PurchaseDateRequestDto.class))).thenReturn(returnMessage);

        mockMvc.perform(RestDocumentationRequestBuilders.patch("/admin/purchase/{purchaseId}", purchaseId)
                        .content(objectMapper.writeValueAsString(purchaseDateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andDo(print())
                .andDo(document("admin/purchase/patch-date",
                        pathParameters(
                                parameterWithName("purchaseId").description("구매 요청 아이디")
                        )))
                .andExpect(status().isOk())
                .andExpect(content().string(returnMessage))
                .andReturn();
    }

    @Test
    @DisplayName("성사된 거래 정보를 저장한다.")
    void insertTransaction() throws Exception {
        Long purchaseId = 1L;
        String returnMessage =  "거래가 성사되었습니다.";
        
        when(manageTransactionService.insertNewTransaction(purchaseId)).thenReturn(returnMessage);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/admin/purchase/success/{purchaseId}", purchaseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andDo(print())
                .andDo(document("admin/purchase/success",
                        pathParameters(
                                parameterWithName("purchaseId").description("성사된 구매 요청 아이디")
                        )))
                .andExpect(status().isOk())
                .andExpect(content().string(returnMessage))
                .andReturn();
    }
}