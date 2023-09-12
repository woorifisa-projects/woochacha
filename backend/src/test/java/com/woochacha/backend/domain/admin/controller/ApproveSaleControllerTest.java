package com.woochacha.backend.domain.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.QueryResults;
import com.woochacha.backend.domain.admin.dto.approve.*;
import com.woochacha.backend.domain.admin.dto.detail.*;
import com.woochacha.backend.domain.admin.service.ApproveSaleService;
import com.woochacha.backend.domain.admin.service.RegisterProductService;
import com.woochacha.backend.domain.admin.dto.RegisterProductDto;
import com.woochacha.backend.domain.car.info.entity.ExchangeType;
import com.woochacha.backend.domain.car.info.entity.ExchangeTypeList;
import com.woochacha.backend.domain.common.CommonTest;
import com.woochacha.backend.domain.status.entity.CarStatusList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ApproveSaleControllerTest extends CommonTest {
    @InjectMocks
    private ApproveSaleController approveSaleController;

    @Mock
    private ApproveSaleService approveSaleService;
    @Mock
    private RegisterProductService registerProductService;

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
    @DisplayName("관리자 페이지에서 모든 차량의 saleform 신청폼(점검 전)을 확인한다.")
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

        when(approveSaleService.getApproveSaleForm(any())).thenReturn(new PageImpl<>(approveSaleResponseDtoList.getResults()));

        mockMvc.perform(get("/admin/sales")
                        .param("pageable", String.valueOf(pageable))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("admin/sales",
                        requestParameters(
                                parameterWithName("pageable").description("페이지네이션")
                        ),
                        responseFields(
                                fieldWithPath("content[].id").description("saleform Id"),
                                fieldWithPath("content[].name").description("판매 요청 회원 이름"),
                                fieldWithPath("content[].carNum").description("판매 차량 번호"),
                                fieldWithPath("content[].status").description("saleform의 현재 상태"),

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
                .andExpect(jsonPath("$.content[0].id").value(approveSaleResponseDto.getId()))
                .andExpect(jsonPath("$.content[0].name").value(approveSaleResponseDto.getName()))
                .andExpect(jsonPath("$.content[0].carNum").value(approveSaleResponseDto.getCarNum()))
                .andExpect(jsonPath("$.content[0].status").value(approveSaleResponseDto.getStatus().name()))
                .andReturn();
    }

    @Test
    @DisplayName("점검 후 해당 매물이 판매 불가 제품일 때 반려를 한다.")
    void denySaleForm() throws Exception {
        Long saleFormId = 1L;
        when(approveSaleService.updateSaleFormDenyStatus(saleFormId)).thenReturn(true);

        mockMvc.perform(RestDocumentationRequestBuilders.patch("/admin/sales/deny/{saleFormId}", saleFormId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("saleForm/deny-apply",
                        pathParameters(
                                parameterWithName("saleFormId").description("판매 요청글 아이디")
                        )))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(true)))
                .andReturn();
    }

    @Test
    void qldbCarInfo() throws Exception {
        Long saleFormId = 1L;
        CarAccidentInfoDto carAccidentInfoDto = CarAccidentInfoDto.builder()
                .accidentType("교통사고")
                .accidentDesc("측면 충돌 사고")
                .accidentDate("2021-4-20")
                .build();
        CarExchangeInfoDto carExchangeInfoDto = CarExchangeInfoDto.builder()
                .exchangeType("트렁크")
                .exchangeDesc("추돌 사고로 인한 교체")
                .exchangeDate("2023-7-16")
                .build();
        String carNum = "76나9156";
        String carOwnerName="류빈용";
        String carOwnerPhone="01049153041";
        int carDistance=90000;
        ExchangeType exchangeType = new ExchangeType((short) 1, ExchangeTypeList.본네트);

        List<CarExchangeInfoDto> carExchangeInfoDtoList = new ArrayList<>();
        carExchangeInfoDtoList.add(carExchangeInfoDto);

        List<CarAccidentInfoDto> carAccidentInfoDtoList = new ArrayList<>();
        List<ExchangeType> exchangeTypeList = new ArrayList<>();
        carAccidentInfoDtoList.add(carAccidentInfoDto);
        exchangeTypeList.add(exchangeType);

        CarInspectionInfoResponseDto carInspectionInfoResponseDto = CarInspectionInfoResponseDto.builder()
                .carNum(carNum)
                .carOwnerName(carOwnerName)
                .carOwnerPhone(carOwnerPhone)
                .carDistance(carDistance)
                .carAccidentInfoDtoList(carAccidentInfoDtoList)
                .carExchangeInfoDtoList(carExchangeInfoDtoList)
                .exchangeTypeList(exchangeTypeList)
                .build();

        when(approveSaleService.getQldbCarInfoList(saleFormId)).thenReturn(carInspectionInfoResponseDto);
        mockMvc.perform(RestDocumentationRequestBuilders.get("/admin/sales/approve/{saleFormId}", saleFormId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("saleForm/get-approve",
                        pathParameters(
                                parameterWithName("saleFormId").description("판매 요청글 아이디")
                        ),
                        responseFields(
                                fieldWithPath("carNum").description("판매 차량 번호"),
                                fieldWithPath("carOwnerName").description("판매 차량 소유주 이름"),
                                fieldWithPath("carOwnerPhone").description("판매 차량 소유주 핸드폰 번호"),
                                fieldWithPath("carDistance").description("판매 차량 주행거리"),
                                fieldWithPath("carAccidentInfoDtoList[0].accidentType").description("차량 사고 종류 업데이트"),
                                fieldWithPath("carAccidentInfoDtoList[0].accidentDesc").description("차량 사고 내역 업데이트"),
                                fieldWithPath("carAccidentInfoDtoList[0].accidentDate").description("차량 사고 날짜 업데이트"),
                                fieldWithPath("carExchangeInfoDtoList[0].exchangeType").description("차량 교체 종류 업데이트"),
                                fieldWithPath("carExchangeInfoDtoList[0].exchangeDesc").description("차량 교체 내역 업데이트"),
                                fieldWithPath("carExchangeInfoDtoList[0].exchangeDate").description("차량 교체 날짜 업데이트"),
                                fieldWithPath("exchangeTypeList[0].id").description("교체 종류 id값"),
                                fieldWithPath("exchangeTypeList[0].type").description("교체 종류 명")
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carNum").value(carInspectionInfoResponseDto.getCarNum()))
                .andExpect(jsonPath("$.carOwnerName").value(carInspectionInfoResponseDto.getCarOwnerName()))
                .andExpect(jsonPath("$.carOwnerPhone").value(carInspectionInfoResponseDto.getCarOwnerPhone()))
                .andExpect(jsonPath("$.carDistance").value(carInspectionInfoResponseDto.getCarDistance()))
                .andReturn();
    }

    @Test
    void compareCarInfo() throws Exception {
        Long saleFormId = 1L;
        CarAccidentInfoDto carAccidentInfoDto = CarAccidentInfoDto.builder()
                .accidentType("교통사고")
                .accidentDesc("측면 충돌 사고")
                .accidentDate("2021-4-20")
                .build();
        CarExchangeInfoDto carExchangeInfoDto = CarExchangeInfoDto.builder()
                .exchangeType("트렁크")
                .exchangeDesc("추돌 사고로 인한 교체")
                .exchangeDate("2023-7-16")
                .build();
        CompareRequestDto compareRequestDto = CompareRequestDto.builder()
                .distance(10000000)
                .carAccidentInfoDto(carAccidentInfoDto)
                .carExchangeInfoDto(carExchangeInfoDto)
                .build();

        when(approveSaleService.compareCarHistory(any(),any())).thenReturn(true);

        mockMvc.perform(RestDocumentationRequestBuilders.patch("/admin/sales/approve/{saleFormId}", saleFormId)
                        .content(objectMapper.writeValueAsString(compareRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("saleForm/approve-status",
                        pathParameters(
                                parameterWithName("saleFormId").description("판매 요청글 아이디")
                        ),
                        requestFields(
                                fieldWithPath("distance").description("차량 주행거리 업데이트"),
                                fieldWithPath("carAccidentInfoDto.accidentType").description("차량 사고 종류 업데이트"),
                                fieldWithPath("carAccidentInfoDto.accidentDesc").description("차량 사고 내역 업데이트"),
                                fieldWithPath("carAccidentInfoDto.accidentDate").description("차량 사고 날짜 업데이트"),
                                fieldWithPath("carExchangeInfoDto.exchangeType").description("차량 교체 종류 업데이트"),
                                fieldWithPath("carExchangeInfoDto.exchangeDesc").description("차량 교체 내역 업데이트"),
                                fieldWithPath("carExchangeInfoDto.exchangeDate").description("차량 교체 날짜 업데이트")
                        )))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(true)))
                .andReturn();
    }

    @Test
    void registerProductInfo() throws Exception {
        Long saleFormId = 1L;
        RegisterProductBasicInfo registerProductBasicInfo = RegisterProductBasicInfo.builder()
                .title("스즈키 더 뉴 C클래스 C200 W205 2019")
                .carName("더 뉴 C클래스 C200 W205")
                .carNum("76나9156")
                .branch("서울")
                .build();

        RegisterProductAccidentInfo carAccidentInfoDto = RegisterProductAccidentInfo.builder()
                .type("교통사고")
                .date("2021-4-20")
                .build();
        RegisterProductExchangeInfo carExchangeInfoDto = RegisterProductExchangeInfo.builder()
                .type("트렁크")
                .date("2023-7-16")
                .build();
        List<RegisterProductAccidentInfo> carAccidentInfoDtoList = new ArrayList<>();
        carAccidentInfoDtoList.add(carAccidentInfoDto);

        List<RegisterProductExchangeInfo> carExchangeInfoDtoList = new ArrayList<>();
        carExchangeInfoDtoList.add(carExchangeInfoDto);
        RegisterProductDetailInfo registerProductDetailInfo = RegisterProductDetailInfo.builder()
                .model("스즈키")
                .color("초록색")
                .year((short) 2019)
                .capacity((short) 6)
                .distance(90000)
                .carType("승합")
                .fuelName("기타")
                .transmissionName("세미오토")
                .produdctAccidentInfoList(carAccidentInfoDtoList)
                .productExchangeInfoList(carExchangeInfoDtoList)
                .build();

        RegisterProductOptionInfo registerProductOptionInfo = RegisterProductOptionInfo.builder()
                .option("열선시트")
                .whether((byte) 0)
                .build();
        List<RegisterProductOptionInfo> registerProductOptionInfos = new ArrayList<>();
        registerProductOptionInfos.add(registerProductOptionInfo);

        RegisterProductDto registerProductDto = RegisterProductDto.builder()
                .registerProductBasicInfo(registerProductBasicInfo)
                .registerProductDetailInfo(registerProductDetailInfo)
                .registerProductOptionInfos(registerProductOptionInfos)
                .build();

        when(registerProductService.getRegisterProductInfo(saleFormId)).thenReturn(registerProductDto);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/admin/sales/register/{saleFormId}", saleFormId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("saleForm/get-product-info",
                        pathParameters(
                                parameterWithName("saleFormId").description("판매 요청글 아이디")
                        ),
                        responseFields(
                                fieldWithPath("registerProductBasicInfo.title").description("차량 제목"),
                                fieldWithPath("registerProductBasicInfo.carName").description("차량 이름"),
                                fieldWithPath("registerProductBasicInfo.carNum").description("차량 번호"),
                                fieldWithPath("registerProductBasicInfo.branch").description("차고지 명"),
                                fieldWithPath("registerProductDetailInfo.model").description("차량 모델"),
                                fieldWithPath("registerProductDetailInfo.color").description("차량 색상"),
                                fieldWithPath("registerProductDetailInfo.year").description("차량 제조일"),
                                fieldWithPath("registerProductDetailInfo.capacity").description("차량 인승"),
                                fieldWithPath("registerProductDetailInfo.distance").description("차량 주행거리"),
                                fieldWithPath("registerProductDetailInfo.carType").description("차량 타입"),
                                fieldWithPath("registerProductDetailInfo.fuelName").description("차량 연료"),
                                fieldWithPath("registerProductDetailInfo.transmissionName").description("차량 기어 종류"),
                                fieldWithPath("registerProductDetailInfo.produdctAccidentInfoList[0].type").description("사고 종류"),
                                fieldWithPath("registerProductDetailInfo.produdctAccidentInfoList[0].date").description("사고 날짜"),
                                fieldWithPath("registerProductDetailInfo.productExchangeInfoList[0].type").description("교체 종류"),
                                fieldWithPath("registerProductDetailInfo.productExchangeInfoList[0].date").description("교체 날짜"),
                                fieldWithPath("registerProductOptionInfos[0].option").description("차량 옵션 명"),
                                fieldWithPath("registerProductOptionInfos[0].whether").description("차량 옵션 존재 여부")
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registerProductBasicInfo.title").value(registerProductBasicInfo.getTitle()))
                .andExpect(jsonPath("$.registerProductBasicInfo.carName").value(registerProductBasicInfo.getCarName()))
                .andExpect(jsonPath("$.registerProductBasicInfo.carNum").value(registerProductBasicInfo.getCarNum()))
                .andExpect(jsonPath("$.registerProductBasicInfo.branch").value(registerProductBasicInfo.getBranch()))
                .andExpect(jsonPath("$.registerProductDetailInfo.model").value(registerProductDetailInfo.getModel()))
                .andExpect(jsonPath("$.registerProductDetailInfo.color").value(registerProductDetailInfo.getColor()))
                .andExpect(jsonPath("$.registerProductDetailInfo.year").value((int)registerProductDetailInfo.getYear()))
                .andExpect(jsonPath("$.registerProductDetailInfo.capacity").value((int)registerProductDetailInfo.getCapacity()))
                .andExpect(jsonPath("$.registerProductDetailInfo.distance").value(registerProductDetailInfo.getDistance()))
                .andExpect(jsonPath("$.registerProductDetailInfo.carType").value(registerProductDetailInfo.getCarType()))
                .andExpect(jsonPath("$.registerProductDetailInfo.fuelName").value(registerProductDetailInfo.getFuelName()))
                .andExpect(jsonPath("$.registerProductDetailInfo.transmissionName").value(registerProductDetailInfo.getTransmissionName()))
                .andExpect(jsonPath("$.registerProductDetailInfo.produdctAccidentInfoList[0].type").value(registerProductDetailInfo.getProdudctAccidentInfoList().get(0).getType()))
                .andExpect(jsonPath("$.registerProductDetailInfo.produdctAccidentInfoList[0].date").value(registerProductDetailInfo.getProdudctAccidentInfoList().get(0).getDate()))
                .andExpect(jsonPath("$.registerProductDetailInfo.productExchangeInfoList[0].type").value(registerProductDetailInfo.getProductExchangeInfoList().get(0).getType()))
                .andExpect(jsonPath("$.registerProductDetailInfo.productExchangeInfoList[0].date").value(registerProductDetailInfo.getProductExchangeInfoList().get(0).getDate()))
                .andExpect(jsonPath("$.registerProductOptionInfos[0].option").value(registerProductOptionInfos.get(0).getOption()))
                .andExpect(jsonPath("$.registerProductOptionInfos[0].whether").value((int)registerProductOptionInfos.get(0).getWhether()))
                .andReturn();
    }

    @Test
    void registerProduct() {

    }
}