package com.woochacha.backend.domain.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.QueryResults;
import com.woochacha.backend.domain.admin.dto.magageProduct.EditProductDto;
import com.woochacha.backend.domain.admin.dto.magageProduct.ManageProductFormDto;
import com.woochacha.backend.domain.admin.service.ManageProductFormService;
import com.woochacha.backend.domain.common.CommonTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ManageProductControllerTest extends CommonTest {

    @InjectMocks
    private ManageProductController manageProductController;

    @Mock
    private ManageProductFormService manageProductFormService;

    @BeforeEach
    public void init(RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.standaloneSetup(manageProductController)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("매물 관리 리스트에서 수정/신청폼을 조회한다.")
    void getRequestForm() throws Exception {
        ManageProductFormDto manageProductFormDto = ManageProductFormDto.builder()
                .productId(1L)
                .title("기아 올 뉴 카니발 2018년형")
                .sellerName("홍길동")
                .sellerEmail("user@woochacha.com")
                .manageType("삭제")
                .build();

        List<ManageProductFormDto> result = new ArrayList<>();
        result.add(manageProductFormDto);

        QueryResults<ManageProductFormDto> purchaseFormListResponseDtoList = new QueryResults<>(result, 5L, 0L, 10L);

        int page = 0, size = 5;

        when(manageProductFormService.findDeleteEditForm(page, size)).thenReturn(new PageImpl<>(purchaseFormListResponseDtoList.getResults()));

        mockMvc.perform(get("/admin/product")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("admin/get-product",
                        requestParameters(
                                parameterWithName("page").description("페이지 오프셋"),
                                parameterWithName("size").description("페이지당 보여줄 매물 개수")
                        ),
                        responseFields(
                                fieldWithPath("content[].productId").description("판매글 아이디"),
                                fieldWithPath("content[].title").description("게시글 제목"),
                                fieldWithPath("content[].sellerName").description("판매자명"),
                                fieldWithPath("content[].sellerEmail").description("판매자 이메일"),
                                fieldWithPath("content[].manageType").description("신청폼 종류"),

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
                .andExpect(jsonPath("$.content[0].productId").value(manageProductFormDto.getProductId()))
                .andExpect(jsonPath("$.content[0].title").value(manageProductFormDto.getTitle()))
                .andExpect(jsonPath("$.content[0].sellerName").value(manageProductFormDto.getSellerName()))
                .andExpect(jsonPath("$.content[0].sellerEmail").value(manageProductFormDto.getSellerEmail()))
                .andExpect(jsonPath("$.content[0].manageType").value(manageProductFormDto.getManageType()))
                .andReturn();
    }

    @Test
    @DisplayName("매물 삭제 신청을 승인한다.")
    void permitDeleteRequest() throws Exception {
        Long productId = 1L;
        String returnMessage = "삭제가 완료되었습니다.";

        doNothing().when(manageProductFormService).deleteProduct(productId);

        mockMvc.perform(RestDocumentationRequestBuilders.patch("/admin/product/delete/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andDo(print())
                .andDo(document("admin/delete-product",
                        pathParameters(
                                parameterWithName("productId").description("삭제할 매물 아이디")
                        )))
                .andExpect(status().isOk())
                .andExpect(content().string(returnMessage))
                .andReturn();
    }

    @Test
    @DisplayName("매물 가격 수정 처리 전 매물 정보를 조회한다.")
    void getEditForm() throws Exception {
        EditProductDto editProductDto = EditProductDto.builder()
                .title("기아 올 뉴 카니발 2018년형")
                .imageUrl("https://woochacha.s3.ap-northeast-2.amazonaws.com/product/1")
                .price(1500)
                .updatePrice(1300)
                .build();
        Long productId = 1L;

        when(manageProductFormService.findEditForm(productId)).thenReturn(editProductDto);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/admin/product/edit/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("admin/get-edit-product",
                        pathParameters(
                                parameterWithName("productId").description("가격을 수정할 매물 아이디")
                        ),
                        responseFields(
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("imageUrl").description("수정할 매물 이미지 경로"),
                                fieldWithPath("price").description("기존 판매 가격"),
                                fieldWithPath("updatePrice").description("수정할 판매 가격")
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(editProductDto.getTitle()))
                .andExpect(jsonPath("$.imageUrl").value(editProductDto.getImageUrl()))
                .andExpect(jsonPath("$.price").value(editProductDto.getPrice()))
                .andExpect(jsonPath("$.updatePrice").value(editProductDto.getUpdatePrice()))
                .andReturn();
    }

    @Test
    @DisplayName("매물 가격 수정을 반려한다.")
    void denyEditRequest() throws Exception {
        Long productId = 1L;
        String returnMessage = "가격 변경이 반려되었습니다.";

        doNothing().when(manageProductFormService).denyEditRequest(productId);

        mockMvc.perform(RestDocumentationRequestBuilders.patch("/admin/product/edit/deny/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andDo(print())
                .andDo(document("admin/deny-edit-product",
                        pathParameters(
                                parameterWithName("productId").description("가격 수정을 반려할 매물 아이디")
                        )))
                .andExpect(status().isOk())
                .andExpect(content().string(returnMessage))
                .andReturn();
    }

    @Test
    @DisplayName("매물 가격 수정을 승인한다.")
    void permitEditRequest() throws Exception {
        Long productId = 1L;
        String returnMessage = "가격 변경이 완료되었습니다.";

        doNothing().when(manageProductFormService).permitEditRequest(productId);

        mockMvc.perform(RestDocumentationRequestBuilders.patch("/admin/product/edit/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andDo(print())
                .andDo(document("admin/approve-edit-product",
                        pathParameters(
                                parameterWithName("productId").description("가격 수정을 승인할 매물 아이디")
                        )))
                .andExpect(status().isOk())
                .andExpect(content().string(returnMessage))
                .andReturn();
    }
}