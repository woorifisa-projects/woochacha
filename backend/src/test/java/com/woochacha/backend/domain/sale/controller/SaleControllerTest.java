package com.woochacha.backend.domain.sale.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woochacha.backend.domain.common.CommonTest;
import com.woochacha.backend.domain.sale.dto.BranchDto;
import com.woochacha.backend.domain.sale.dto.SaleFormRequestDto;
import com.woochacha.backend.domain.sale.service.SaleFormApplyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SaleControllerTest extends CommonTest {

    @InjectMocks
    private SaleController saleController;

    @Mock
    private SaleFormApplyService saleFormApplyService;

    @BeforeEach
    public void init(RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.standaloneSetup(saleController)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("지점 리스트를 조회한다.")
    void carSaleForm() throws Exception {
        BranchDto branchDto = new BranchDto(1L, "서울");
        List<BranchDto> branchDtoList = new ArrayList<>();
        branchDtoList.add(branchDto);

        when(saleFormApplyService.getBranchList()).thenReturn(branchDtoList);

        mockMvc.perform(get("/products/sale")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("sale/get-branch",
                        responseFields(
                                fieldWithPath("[].id").description("지점 아이디"),
                                fieldWithPath("[].name").description("지점 이름")
                )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(branchDto.getId()))
                .andExpect(jsonPath("$.[0].name").value(branchDto.getName()))
                .andReturn();
    }

    @Test
    void submitCarSaleForm() throws Exception {
        SaleFormRequestDto saleFormRequestDto = new SaleFormRequestDto("22가1111", 1L, 2L);
        String returnMessage = "차량 판매 신청이 성공적으로 완료되었습니다.";

        when(saleFormApplyService.submitCarSaleForm(saleFormRequestDto.getCarNum(), saleFormRequestDto.getMemberId(), saleFormRequestDto.getBranchId())).thenReturn(returnMessage);

        mockMvc.perform(post("/products/sale")
                        .content(objectMapper.writeValueAsString(saleFormRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andDo(print())
                .andDo(document("sale/submit-form"))
                .andExpect(status().isOk())
                .andExpect(content().string(returnMessage))
                .andReturn();
    }
}