package com.woochacha.backend.domain.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.QueryResults;
import com.woochacha.backend.domain.admin.dto.approve.ApproveSaleResponseDto;
import com.woochacha.backend.domain.admin.service.ApproveSaleService;
import com.woochacha.backend.domain.common.CommonTest;
import com.woochacha.backend.domain.status.entity.CarStatusList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApproveSaleControllerTest extends CommonTest {
    @InjectMocks
    private ApproveSaleController approveSaleController;

    @Mock
    private ApproveSaleService approveSaleService;

    @BeforeEach
    public void init(RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.standaloneSetup(approveSaleController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void allSaleForms() throws Exception {
        ApproveSaleResponseDto approveSaleResponseDto = ApproveSaleResponseDto.builder()
                .id(2L)
                .name("주언반")
                .carNum("74사4095")
                .status(CarStatusList.심사중)
                .build();
        List<ApproveSaleResponseDto> result = new ArrayList<>();
        result.add(approveSaleResponseDto);

        QueryResults<ApproveSaleResponseDto> approveSaleResponseDtoList = new QueryResults<>(result, 5L, 0L, 10L);

        Pageable pageable = PageRequest.of(0,5);

        when(approveSaleService.getApproveSaleForm(any())).thenReturn(
                new PageImpl<>(
                        approveSaleResponseDtoList.getResults(), pageable, approveSaleResponseDtoList.getTotal()));

        mockMvc.perform(get("/admin/sales")
                        .param("pageable", String.valueOf(pageable))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
//                .andDo(document("admin/sales",
//                        requestParameters(
//                                parameterWithName("pageable").description("페이지네이션")
//                        ),
//                        responseFields(
//                                fieldWithPath("content[].id").description("saleform Id"),
//                                fieldWithPath("content[].name").description("판매 요청 회원 이름"),
//                                fieldWithPath("content[].carNum").description("판매 차량 번호"),
//                                fieldWithPath("content[].status").description("saleform의 현재 상태"),
//
//                                fieldWithPath("pageable").description("페이징 정보"),
//                                fieldWithPath("last").description("마지막 페이지 여부"),
//                                fieldWithPath("totalPages").description("총 페이지 수"),
//                                fieldWithPath("totalElements").description("총 요소 수"),
//                                fieldWithPath("size").description("페이지 크기"),
//                                fieldWithPath("number").description("현재 페이지 번호"),
//                                fieldWithPath("sort.empty").description("정렬 여부 (비어 있음)"),
//                                fieldWithPath("sort.sorted").description("정렬 여부 (정렬됨)"),
//                                fieldWithPath("sort.unsorted").description("정렬 여부 (정렬되지 않음)"),
//
//                                fieldWithPath("numberOfElements").description("현재 페이지의 요소 수"),
//                                fieldWithPath("first").description("첫 번째 페이지 여부"),
//                                fieldWithPath("empty").description("결과가 비어 있는지 여부")
//                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(approveSaleResponseDto.getId()))
                .andExpect(jsonPath("$.content[0].name").value(approveSaleResponseDto.getName()))
                .andExpect(jsonPath("$.content[0].carNum").value(approveSaleResponseDto.getCarNum()))
                .andExpect(jsonPath("$.content[0].status").value(approveSaleResponseDto.getStatus().name()))
                .andReturn();
    }

    @Test
    void denySaleForm() {
    }

    @Test
    void qldbCarInfo() {
    }

    @Test
    void compareCarInfo() {
    }

    @Test
    void registerProductInfo() {
    }

    @Test
    void registerProduct() {
    }
}