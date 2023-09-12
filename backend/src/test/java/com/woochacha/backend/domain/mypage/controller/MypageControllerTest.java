package com.woochacha.backend.domain.mypage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woochacha.backend.domain.common.CommonTest;
import com.woochacha.backend.domain.mypage.dto.*;
import com.woochacha.backend.domain.mypage.service.MypageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MypageControllerTest extends CommonTest {

    @InjectMocks
    private MypageController mypageController;

    @Mock
    private MypageService mypageService;

    @BeforeEach
    public void init(RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.standaloneSetup(mypageController)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("회원은 자신이 등록한 매물을 조회한다.")
    void registeredProductList() throws Exception {
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .title("기아 올 뉴 카니발 2018년형")
                .distance(110000)
                .branch("서울")
                .price(2690)
                .imageUrl("https://woochacha.s3.ap-northeast-2.amazonaws.com/product/22나2222/1")
                .productId(14L)
                .status("판매중")
                .build();

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        productResponseDtoList.add(productResponseDto);

        Long memberId = 17L;
        int page = 0;
        int size = 5;

        Page<ProductResponseDto> productsPage = new PageImpl<>(productResponseDtoList);

        when(mypageService.getRegisteredProductsByMemberId(memberId, page, size)).thenReturn(productsPage);

        mockMvc.perform(get("/mypage/registered/{memberId}", memberId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("mypage/registered-list",
                        pathParameters(
                                parameterWithName("memberId").description("회원 아이디")
                        ),
                        requestParameters(
                                parameterWithName("page").description("페이지 오프셋"),
                                parameterWithName("size").description("페이지당 보여줄 매물 개수")
                        ),
                        responseFields(
                                fieldWithPath("content[].title").description("제목(모델+차량명+연식)"),
                                fieldWithPath("content[].distance").description("주행 거리"),
                                fieldWithPath("content[].branch").description("판매 지점"),
                                fieldWithPath("content[].price").description("판매 가격"),
                                fieldWithPath("content[].imageUrl").description("이미지 리스트"),
                                fieldWithPath("content[].productId").description("아이디"),
                                fieldWithPath("content[].status").description("매물 판매 상태"),

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

                .andExpect(jsonPath("$.content[0].title").value(productResponseDto.getTitle()))
                .andExpect(jsonPath("$.content[0].distance").value(productResponseDto.getDistance()))
                .andExpect(jsonPath("$.content[0].branch").value(productResponseDto.getBranch()))
                .andExpect(jsonPath("$.content[0].price").value(productResponseDto.getPrice()))
                .andExpect(jsonPath("$.content[0].imageUrl").value(productResponseDto.getImageUrl()))
                .andExpect(jsonPath("$.content[0].productId").value(productResponseDto.getProductId()))
                .andExpect(jsonPath("$.content[0].status").value(productResponseDto.getStatus()))
                .andReturn();
    }

    @Test
    @DisplayName("회원은 판매한 이력을 조회한다.")
    void soldProductList() throws Exception {
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .title("기아 올 뉴 카니발 2018년형")
                .distance(110000)
                .branch("서울")
                .price(2690)
                .imageUrl("https://woochacha.s3.ap-northeast-2.amazonaws.com/product/22나2222/1")
                .productId(14L)
                .status("판매중")
                .build();

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        productResponseDtoList.add(productResponseDto);

        Long memberId = 17L;
        int page = 0;
        int size = 5;

        Page<ProductResponseDto> productsPage = new PageImpl<>(productResponseDtoList);

        when(mypageService.getSoldProductsByMemberId(memberId, page, size)).thenReturn(productsPage);


        mockMvc.perform(get("/mypage/sale/{memberId}", memberId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("mypage/sale-list",
                        pathParameters(
                                parameterWithName("memberId").description("회원 아이디")
                        ),
                        requestParameters(
                                parameterWithName("page").description("페이지 오프셋"),
                                parameterWithName("size").description("페이지당 보여줄 매물 개수")
                        ),
                        responseFields(
                                fieldWithPath("content[].title").description("제목(모델+차량명+연식)"),
                                fieldWithPath("content[].distance").description("주행 거리"),
                                fieldWithPath("content[].branch").description("판매 지점"),
                                fieldWithPath("content[].price").description("판매 가격"),
                                fieldWithPath("content[].imageUrl").description("이미지 리스트"),
                                fieldWithPath("content[].productId").description("아이디"),
                                fieldWithPath("content[].status").description("매물 판매 상태"),

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

                .andExpect(jsonPath("$.content[0].title").value(productResponseDto.getTitle()))
                .andExpect(jsonPath("$.content[0].distance").value(productResponseDto.getDistance()))
                .andExpect(jsonPath("$.content[0].branch").value(productResponseDto.getBranch()))
                .andExpect(jsonPath("$.content[0].price").value(productResponseDto.getPrice()))
                .andExpect(jsonPath("$.content[0].imageUrl").value(productResponseDto.getImageUrl()))
                .andExpect(jsonPath("$.content[0].productId").value(productResponseDto.getProductId()))
                .andExpect(jsonPath("$.content[0].status").value(productResponseDto.getStatus()))
                .andReturn();
    }

    @Test
    @DisplayName("회원은 구매한 이력을 조회한다.")
    void purchasedProductList() throws Exception {
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .title("기아 올 뉴 카니발 2018년형")
                .distance(110000)
                .branch("서울")
                .price(2690)
                .imageUrl("https://woochacha.s3.ap-northeast-2.amazonaws.com/product/22나2222/1")
                .productId(14L)
                .status("판매중")
                .build();

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        productResponseDtoList.add(productResponseDto);

        Long memberId = 17L;
        int page = 0;
        int size = 5;

        Page<ProductResponseDto> productsPage = new PageImpl<>(productResponseDtoList);

        when(mypageService.getPurchaseProductsByMemberId(memberId, page, size)).thenReturn(productsPage);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/mypage/purchase/{memberId}", memberId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("mypage/purchase-list",
                        pathParameters(
                                parameterWithName("memberId").description("회원 아이디")
                        ),
                        requestParameters(
                                parameterWithName("page").description("페이지 오프셋"),
                                parameterWithName("size").description("페이지당 보여줄 매물 개수")
                        ),
                        responseFields(
                                fieldWithPath("content[].title").description("제목(모델+차량명+연식)"),
                                fieldWithPath("content[].distance").description("주행 거리"),
                                fieldWithPath("content[].branch").description("판매 지점"),
                                fieldWithPath("content[].price").description("판매 가격"),
                                fieldWithPath("content[].imageUrl").description("이미지 리스트"),
                                fieldWithPath("content[].productId").description("아이디"),
                                fieldWithPath("content[].status").description("매물 판매 상태"),

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

                .andExpect(jsonPath("$.content[0].title").value(productResponseDto.getTitle()))
                .andExpect(jsonPath("$.content[0].distance").value(productResponseDto.getDistance()))
                .andExpect(jsonPath("$.content[0].branch").value(productResponseDto.getBranch()))
                .andExpect(jsonPath("$.content[0].price").value(productResponseDto.getPrice()))
                .andExpect(jsonPath("$.content[0].imageUrl").value(productResponseDto.getImageUrl()))
                .andExpect(jsonPath("$.content[0].productId").value(productResponseDto.getProductId()))
                .andExpect(jsonPath("$.content[0].status").value(productResponseDto.getStatus()))
                .andReturn();
    }

    @Test
    @DisplayName("회원은 프로필을 조회한다.")
    void mypage() throws Exception {
        ProfileDto profileDto = ProfileDto.builder()
                .profileImage("https://woochacha.s3.ap-northeast-2.amazonaws.com/profile/default")
                .email("user@woochacha.com")
                .name("신짱구")
                .phone("01012345678")
                .build();

        Long memberId = 17L;

        when(mypageService.getProfileByMemberId(memberId)).thenReturn(profileDto);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/mypage/{memberId}", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("mypage/get-profile",
                        pathParameters(
                                parameterWithName("memberId").description("회원 아이디")
                        ),
                        responseFields(
                                fieldWithPath("profileImage").description("프로필 사진"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("phone").description("핸드폰 번호")
                        )))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.profileImage").value(profileDto.getProfileImage()))
                .andExpect(jsonPath("$.email").value(profileDto.getEmail()))
                .andExpect(jsonPath("$.name").value(profileDto.getName()))
                .andExpect(jsonPath("$.phone").value(profileDto.getPhone()))
                .andReturn();
    }

    @Test
    @DisplayName("회원은 작성한 판매 신청폼을 확인한다.")
    void retrievePosts() throws Exception {
        SaleFormDto saleFormDto = SaleFormDto.builder()
                .carNum("12가3456")
                .createdAt(LocalDateTime.now())
                .branch("서울")
                .carStatus("심사중")
                .build();

        List<SaleFormDto> saleFormDtoList = new ArrayList<>();
        saleFormDtoList.add(saleFormDto);

        Long memberId = 17L;
        int page = 0;
        int size = 5;

        Page<SaleFormDto> saleFormDtoPage = new PageImpl<>(saleFormDtoList);

        when(mypageService.getSaleFormsByMemberId(memberId, page, size)).thenReturn(saleFormDtoPage);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/mypage/sale-request/{memberId}", memberId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("mypage/get-sale-request",
                        pathParameters(
                                parameterWithName("memberId").description("회원 아이디")
                        ),
                        requestParameters(
                                parameterWithName("page").description("페이지 오프셋"),
                                parameterWithName("size").description("페이지당 보여줄 매물 개수")
                        ),
                        responseFields(
                                fieldWithPath("content[].carNum").description("차량 번호"),
                                fieldWithPath("content[].createdAt").description("신청일"),
                                fieldWithPath("content[].branch").description("판매 지점"),
                                fieldWithPath("content[].carStatus").description("매물 심사 상태"),

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

                .andExpect(jsonPath("$.content[0].carNum").value(saleFormDto.getCarNum()))
                .andExpect(jsonPath("$.content[0].createdAt").exists())
                .andExpect(jsonPath("$.content[0].branch").value(saleFormDto.getBranch()))
                .andExpect(jsonPath("$.content[0].carStatus").value(saleFormDto.getCarStatus()))
                .andReturn();
    }

    @Test
    @DisplayName("회원은 구매 신청폼을 조회한다.")
    void purchaseRequestList() throws Exception {
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .title("기아 올 뉴 카니발 2018년형")
                .distance(110000)
                .branch("서울")
                .price(2690)
                .imageUrl("https://woochacha.s3.ap-northeast-2.amazonaws.com/product/22나2222/1")
                .productId(14L)
                .status("판매중")
                .build();

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        productResponseDtoList.add(productResponseDto);

        Page<ProductResponseDto> productsPage = new PageImpl<>(productResponseDtoList);

        Long memberId = 17L;
        int page = 0;
        int size = 5;

        when(mypageService.getPurchaseRequestByMemberId(memberId, page, size)).thenReturn(productsPage);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/mypage/purchase-request/{memberId}", memberId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("mypage/get-purchase-request",
                        pathParameters(
                                parameterWithName("memberId").description("회원 아이디")
                        ),
                        requestParameters(
                                parameterWithName("page").description("페이지 오프셋"),
                                parameterWithName("size").description("페이지당 보여줄 매물 개수")
                        ),
                        responseFields(
                                fieldWithPath("content[].title").description("제목(모델+차량명+연식)"),
                                fieldWithPath("content[].distance").description("주행 거리"),
                                fieldWithPath("content[].branch").description("판매 지점"),
                                fieldWithPath("content[].price").description("판매 가격"),
                                fieldWithPath("content[].imageUrl").description("이미지 리스트"),
                                fieldWithPath("content[].productId").description("아이디"),
                                fieldWithPath("content[].status").description("매물 판매 상태"),

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

                .andExpect(jsonPath("$.content[0].title").value(productResponseDto.getTitle()))
                .andExpect(jsonPath("$.content[0].distance").value(productResponseDto.getDistance()))
                .andExpect(jsonPath("$.content[0].branch").value(productResponseDto.getBranch()))
                .andExpect(jsonPath("$.content[0].price").value(productResponseDto.getPrice()))
                .andExpect(jsonPath("$.content[0].imageUrl").value(productResponseDto.getImageUrl()))
                .andExpect(jsonPath("$.content[0].productId").value(productResponseDto.getProductId()))
                .andExpect(jsonPath("$.content[0].status").value(productResponseDto.getStatus()))
                .andReturn();
    }

    @Test
    @DisplayName("회원은 상품 수정 신청폼을 조회한다.")
    void getProductEditForm() throws Exception {
        Long memberId = 17L;
        Long productId = 45L;

        EditProductDto editProductDto = EditProductDto.builder()
                .title("기아 올 뉴 카니발 2018년형")
                .price(2690)
                .carImage("https://woochacha.s3.ap-northeast-2.amazonaws.com/product/22나2222/1")
                .build();

        when(mypageService.getProductEditRequestInfo(memberId, productId)).thenReturn(editProductDto);

        mockMvc.perform(get("/mypage/registered/edit")
                        .param("memberId", String.valueOf(memberId))
                        .param("productId", String.valueOf(productId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("mypage/get-registered-edit",
                        requestParameters(
                                parameterWithName("memberId").description("회원 아이디"),
                                parameterWithName("productId").description("매물 아이디")
                        ),
                        responseFields(
                                fieldWithPath("title").description("제목(모델+차량명+연식)"),
                                fieldWithPath("price").description("판매 가격"),
                                fieldWithPath("carImage").description("이미지 리스트")
                        )))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.title").value(editProductDto.getTitle()))
                .andExpect(jsonPath("$.price").value(editProductDto.getPrice()))
                .andExpect(jsonPath("$.carImage").value(editProductDto.getCarImage()))
                .andReturn();
    }

    @Test
    @DisplayName("회원은 상품 수정 신청폼을 제출한다.")
    void editProductEditForm() throws Exception {
        Long memberId = 17L;
        Long productId = 45L;
        UpdatePriceDto updatePriceDto = new UpdatePriceDto(3000);
        String returnMessage = "가격 변경 요청이 완료되었습니다.";

        when(mypageService.editProductEditRequestInfo(anyLong(), anyLong(), any(UpdatePriceDto.class))).thenReturn(returnMessage);

        mockMvc.perform(patch("/mypage/registered/edit")
                .content(objectMapper.writeValueAsString(updatePriceDto))
                .param("memberId", String.valueOf(memberId))
                .param("productId", String.valueOf(productId))
                .contentType(MediaType.APPLICATION_JSON)
                 .accept(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andDo(print())
                .andDo(document("mypage/patch-registered-edit",
                        requestParameters(
                                parameterWithName("memberId").description("상품 가격 수정을 요청한 회원 아이디"),
                                parameterWithName("productId").description("수정할 매물 가격")
                        )))
                .andExpect(status().isOk())
                .andExpect(content().string(returnMessage))
                .andReturn();
    }

    @Test
    @DisplayName("회원은 등록한 매물의 삭제를 요청한다.")
    void productDeleteRequest() throws Exception {
        Long productId = 2L;
        Long memberId = 17L;
        String returnMessage = "삭제 신청이 완료되었습니다.";

        when(mypageService.productDeleteRequest(productId, memberId)).thenReturn(returnMessage);

        mockMvc.perform(RestDocumentationRequestBuilders.patch("/mypage/registered/delete/{productId}", productId)
                        .param("memberId", String.valueOf(memberId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andDo(print())
                .andDo(document("mypage/patch-registered-delete",
                        requestParameters(
                                parameterWithName("memberId").description("상품 삭제를 요청한 회원 아이디")
                        ),
                        pathParameters(
                                parameterWithName("productId").description("삭제할 매물 아이디")
                        )))
                .andExpect(status().isOk())
                .andExpect(content().string(returnMessage))
                .andReturn();
    }

    @Test
    @DisplayName("회원은 프로필 이미지를 조회한다.")
    void getProfileForEdit() throws Exception {
        Long memberId = 17L;

        EditProfileDto editProfileDto = EditProfileDto.builder()
                .imageUrl("https://woochacha.s3.ap-northeast-2.amazonaws.com/profile/default")
                .name("신짱구")
                .build();

        when(mypageService.getProfileForEdit(memberId)).thenReturn(editProfileDto);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/mypage/profile/edit/{memberId}", memberId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("mypage/get-profile",
                        pathParameters(
                                parameterWithName("memberId").description("회원 아이디")
                        ),
                        responseFields(
                                fieldWithPath("imageUrl").description("프로필 사진 url"),
                                fieldWithPath("name").description("판매 가격")
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imageUrl").value(editProfileDto.getImageUrl()))
                .andExpect(jsonPath("$.name").value(editProfileDto.getName()))
                .andReturn();
    }

    // TODO: multipartfile 직렬화 문제 해결
//    @Test
//    @DisplayName("회원은 프로필 이미지를 수정한다.")
//    void editProfile() throws Exception {
//        Long memberId = 17L;
//        String newProfileImage = "https://woochacha.s3.ap-northeast-2.amazonaws.com/profile/edit";
//        String returnMessage = "프로필 수정이 완료되었습니다.";
//
////        FileInputStream fileInputStream = new FileInputStream("./com/woochacha/backend/common/user.jpeg");
//
//        MockMultipartFile imageFile = new MockMultipartFile(
//                "multipartFile", // 파라미터 이름 (컨트롤러에서 사용한 이름과 일치해야 함)
//                "user.jpeg",       // 파일 이름
//                MediaType.IMAGE_JPEG.toString(), // 이미지 파일 형식 지정
////                fileInputStream
//                getClass().getResourceAsStream("com/woochacha/backend/common/user.jpeg") // 이미지 파일 경로 지정
//        );
//
//        InputStream inputStream = imageFile.getInputStream();
//
////        MockMultipartHttpServletRequestBuilder builder =
////                RestDocumentationRequestBuilders.
////                        multipart("/mypage/profile/edit/{memberId}", memberId);
////        builder.with(request -> {
////            request.setMethod("PATCH");
////            return request;
////        });
//
//
//        AmazonS3RequestDto amazonS3RequestDto = new AmazonS3RequestDto(imageFile, "user@woochacha.com");
//
//        when(mypageService.editProfile(anyLong(), any(AmazonS3RequestDto.class))).thenReturn(newProfileImage);
//
////        mockMvc.perform(builder
//        mockMvc.perform(RestDocumentationRequestBuilders.multipart("/mypage/profile/edit/{memberId}", memberId)
//                        .content(objectMapper.writeValueAsString(amazonS3RequestDto))
//                        .contentType(MediaType.MULTIPART_FORM_DATA)
//                        .accept(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
//                .andDo(print())
//                .andDo(document("mypage/patch-profile-edit",
//                        pathParameters(
//                                parameterWithName("memberId").description("회원 아이디")
//                        ),
//                        requestFields(
//                                fieldWithPath("multipartFile").description("변경할 프로필 사진"),
//                                fieldWithPath("email").description("사용자 이메일")
//                        ),
//                        responseFields(
//                                fieldWithPath("image").description("변경된 프로필 사진 url"),
//                                fieldWithPath("name").description("판매 가격")
//                        )))
//                .andExpect(status().isOk())
//                .andExpect(content().string(returnMessage))
//                .andReturn();
//    }
}