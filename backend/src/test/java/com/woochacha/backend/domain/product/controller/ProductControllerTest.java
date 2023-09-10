package com.woochacha.backend.domain.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woochacha.backend.domain.car.detail.dto.CarNameDto;
import com.woochacha.backend.domain.car.type.dto.*;
import com.woochacha.backend.domain.common.CommonTest;
import com.woochacha.backend.domain.product.dto.ProductAllResponseDto;
import com.woochacha.backend.domain.product.dto.ProductDetailResponseDto;
import com.woochacha.backend.domain.product.dto.ProductPurchaseRequestDto;
import com.woochacha.backend.domain.product.dto.all.ProductInfo;
import com.woochacha.backend.domain.product.dto.detail.*;
import com.woochacha.backend.domain.product.dto.filter.ProductFilterInfo;
import com.woochacha.backend.domain.product.service.ProductService;
import com.woochacha.backend.domain.sale.dto.BranchDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ProductControllerTest extends CommonTest {
   @InjectMocks
   private ProductController productController;

   @Mock
   private ProductService productService;

   @BeforeEach
   public void init(RestDocumentationContextProvider restDocumentation) {
       mockMvc = MockMvcBuilders.standaloneSetup(productController)
               .apply(documentationConfiguration(restDocumentation)
                       .operationPreprocessors()
                       .withRequestDefaults(prettyPrint())
                       .withResponseDefaults(prettyPrint()))
               .build();
       objectMapper = new ObjectMapper();
   }

   @Test
   @DisplayName("전체 매물 리스트와 필터링 목록을 리턴한다.")
   void findAllProduct() throws Exception {
       ProductInfo productInfo = ProductInfo.builder()
               .id((long) 14)
               .title("기아 올 뉴 카니발 2018년형")
               .distance(110000)
               .branch("서울")
               .price(2690)
               .imageUrl("https://woochacha.s3.ap-northeast-2.amazonaws.com/product/22나2222/1")
               .build();

       List<ProductInfo> productInfoList = new ArrayList<>();
       productInfoList.add(productInfo);

       TypeDto typeDto  = new TypeDto(1, "경차");
       ModelDto modelDto = new ModelDto(1, "현대");
       CarNameDto carNameDto = new CarNameDto(1L, "아반떼 AD");
       FuelDto fuelDto = new FuelDto(1, "가솔린");
       ColorDto colorDto = new ColorDto(1, "검정색");
       TransmissionDto transmissionDto = new TransmissionDto(1, "오토");
       BranchDto branchDto = new BranchDto(1L, "서울");

       List<TypeDto> typeDtoList = new ArrayList<>();
       List<ModelDto> modelDtoList = new ArrayList<>();
       List<CarNameDto> carNameDtoList= new ArrayList<>();
       List<FuelDto> fuelDtoList = new ArrayList<>();
       List<ColorDto> colorDtoList = new ArrayList<>();
       List<TransmissionDto> transmissionDtoList = new ArrayList<>();
       List<BranchDto> branchDtoList = new ArrayList<>();

       typeDtoList.add(typeDto);
       modelDtoList.add(modelDto);
       carNameDtoList.add(carNameDto);
       fuelDtoList.add(fuelDto);
       colorDtoList.add(colorDto);
       transmissionDtoList.add(transmissionDto);
       branchDtoList.add(branchDto);

       ProductFilterInfo productFilterInfo = ProductFilterInfo.builder()
               .typeList(typeDtoList)
               .modelList(modelDtoList)
               .carNameList(carNameDtoList)
               .fuelList(fuelDtoList)
               .colorList(colorDtoList)
               .transmissionList(transmissionDtoList)
               .branchList(branchDtoList)
               .build();

       ProductAllResponseDto productAllResponseDto = new ProductAllResponseDto(productInfoList, productFilterInfo);

       when(productService.findAllProduct()).thenReturn(productAllResponseDto);

       mockMvc.perform(get("/product"))
               .andExpect(status().isOk())
               .andDo(document("product/get-all",
                       responseFields(
                               fieldWithPath("productInfo").description("[기본 정보]"),
                               fieldWithPath("productInfo[].id").description("아이디"),
                               fieldWithPath("productInfo[].title").description("제목(모델+차량명+연식)"),
                               fieldWithPath("productInfo[].distance").description("주행 거리"),
                               fieldWithPath("productInfo[].branch").description("판매 지점"),
                               fieldWithPath("productInfo[].price").description("판매 가격"),
                               fieldWithPath("productInfo[].imageUrl").description("이미지 리스트"),

                               fieldWithPath("productFilterInfo").description("[필터링 종류]"),
                               fieldWithPath("productFilterInfo.typeList[].id").description("차종 리스트 : 아이디"),
                               fieldWithPath("productFilterInfo.typeList[].name").description("차종 리스트 : 내용"),
                               fieldWithPath("productFilterInfo.modelList[].id").description("모델 리스트 : 아이디"),
                               fieldWithPath("productFilterInfo.modelList[].name").description("모델 리스트 : 내용"),
                               fieldWithPath("productFilterInfo.carNameList[].id").description("차량명 리스트 : 아이디"),
                               fieldWithPath("productFilterInfo.carNameList[].name").description("차량명 리스트 : 내용"),
                               fieldWithPath("productFilterInfo.fuelList[].id").description("연료 리스트 : 아이디"),
                               fieldWithPath("productFilterInfo.fuelList[].name").description("연료 리스트 : 내용"),
                               fieldWithPath("productFilterInfo.colorList[].id").description("색상 리스트 : 아이디"),
                               fieldWithPath("productFilterInfo.colorList[].name").description("색상 리스트  : 내용"),
                               fieldWithPath("productFilterInfo.transmissionList[].id").description("변속기 리스트 : 아이디"),
                               fieldWithPath("productFilterInfo.transmissionList[].name").description("변속기 리스트 : 내용"),
                               fieldWithPath("productFilterInfo.branchList[].id").description("판매 지점 리스트 : 아이디"),
                               fieldWithPath("productFilterInfo.branchList[].name").description("판매 지점 리스트 : 내용")
                       )))
               .andDo(print())
               .andExpect(jsonPath("$.productInfo[0].id").value(productAllResponseDto.getProductInfo().get(0).getId()))
               .andExpect(jsonPath("$.productInfo[0].title").value(productAllResponseDto.getProductInfo().get(0).getTitle()))
               .andExpect(jsonPath("$.productInfo[0].distance").value(productAllResponseDto.getProductInfo().get(0).getDistance()))
               .andExpect(jsonPath("$.productInfo[0].branch").value(productAllResponseDto.getProductInfo().get(0).getBranch()))
               .andExpect(jsonPath("$.productInfo[0].price").value(productAllResponseDto.getProductInfo().get(0).getPrice()))
               .andExpect(jsonPath("$.productInfo[0].imageUrl").value(productAllResponseDto.getProductInfo().get(0).getImageUrl()))
               .andExpect(jsonPath("$.productFilterInfo").exists())
               .andReturn();
   }

   @Test
   @DisplayName("상세 매물 정보를 리턴한다.")
   void findDetailProduct() throws Exception {
       ProductBasicInfo productBasicInfo = ProductBasicInfo.builder()
               .title("기아 올 뉴 카니발 2018년형")
               .carNum("22나 2222")
               .branch("서울")
               .price(2690).build();

       ProductAccidentInfo productAccidentInfo = ProductAccidentInfo.builder()
               .type("교통사고").count(2).build();
       List<ProductAccidentInfo> productAccidentInfoList = new ArrayList<>();
       productAccidentInfoList.add(productAccidentInfo);

       ProductExchangeInfo productExchangeInfo = ProductExchangeInfo.builder()
               .type("본네트").count(1).build();
       List<ProductExchangeInfo> productExchangeInfoList = new ArrayList<>();
       productExchangeInfoList.add(productExchangeInfo);

       ProductDetailInfo productDetailInfo = ProductDetailInfo.builder()
               .capacity((short) 9)
               .distance(110000)
               .carType("RV")
               .fuelName("디젤")
               .transmissionName("오토")
               .productAccidentInfoList(productAccidentInfoList)
               .productExchangeInfoList(productExchangeInfoList)
               .build();

       ProductOptionInfo productOption = ProductOptionInfo.builder()
               .option("열선시트")
               .whether((byte) 1)
               .build();
       List<ProductOptionInfo> productOptionInfoList = new ArrayList<>();
       productOptionInfoList.add(productOption);

       ProductOwnerInfo productOwnerInfo = ProductOwnerInfo.builder()
               .sellerName("이소민")
               .sellerEmail("user2@woorifisa.com")
               .sellerProfileImage("https://woochacha.s3.ap-northeast-2.amazonaws.com/profile/user2@woorifisa.com")
               .build();

       List<String> carImageList = new ArrayList<>();
       carImageList.add("https://woochacha.s3.ap-northeast-2.amazonaws.com/product/22나2222/1");

       ProductDetailResponseDto productDetailResponseDto = new ProductDetailResponseDto(
               productBasicInfo, productDetailInfo, productOptionInfoList, productOwnerInfo, carImageList);

       Long productId = 14L;
       when(productService.findDetailProduct(productId)).thenReturn(productDetailResponseDto);

       mockMvc.perform(get("/product/{productId}", productId))
               .andExpect(status().isOk())
               .andDo(document("product/get-detail",
                       responseFields(
                               fieldWithPath("productBasicInfo").description("[기본 정보]"),
                               fieldWithPath("productBasicInfo.title").description("제목(모델+차량명+연식)"),
                               fieldWithPath("productBasicInfo.carNum").description("차량 번호"),
                               fieldWithPath("productBasicInfo.branch").description("판매 지점"),
                               fieldWithPath("productBasicInfo.price").description("판매 가격"),

                               fieldWithPath("productDetailInfo.capacity").description("승차 정원"),
                               fieldWithPath("productDetailInfo.distance").description("주행 거리"),
                               fieldWithPath("productDetailInfo.carType").description("차종"),
                               fieldWithPath("productDetailInfo.fuelName").description("연료"),
                               fieldWithPath("productDetailInfo.transmissionName").description("변속기"),

                               fieldWithPath("productDetailInfo.productAccidentInfoList[].type").description("사고 종류(침수 사고, 교통 사고)"),
                               fieldWithPath("productDetailInfo.productAccidentInfoList[].count").description("사고 횟수"),

                               fieldWithPath("productDetailInfo.productExchangeInfoList[].type").description("부품 교체 부위"),
                               fieldWithPath("productDetailInfo.productExchangeInfoList[].count").description("부품 교체 횟수"),

                               fieldWithPath("productOptionInfo[].option").description("차량 옵션 종류"),
                               fieldWithPath("productOptionInfo[].whether").description("차량 옵션 포함 여부"),

                               fieldWithPath("productOwnerInfo.sellerName").description("판매자 이름"),
                               fieldWithPath("productOwnerInfo.sellerEmail").description("판매자 이메일"),
                               fieldWithPath("productOwnerInfo.sellerProfileImage").description("판매자 프로필 사진"),

                               fieldWithPath("carImageList[]").description("차량 사진 리스트")

                       )))
               .andDo(print())

               .andExpect(jsonPath("$.productBasicInfo.title").value(productDetailResponseDto.getProductBasicInfo().getTitle()))
               .andExpect(jsonPath("$.productBasicInfo.carNum").value(productDetailResponseDto.getProductBasicInfo().getCarNum()))
               .andExpect(jsonPath("$.productBasicInfo.branch").value(productDetailResponseDto.getProductBasicInfo().getBranch()))
               .andExpect(jsonPath("$.productBasicInfo.price").value(productDetailResponseDto.getProductBasicInfo().getPrice()))

               .andExpect(jsonPath("$.productDetailInfo.capacity").value((int) productDetailResponseDto.getProductDetailInfo().getCapacity()))
               .andExpect(jsonPath("$.productDetailInfo.distance").value(productDetailResponseDto.getProductDetailInfo().getDistance()))
               .andExpect(jsonPath("$.productDetailInfo.carType").value(productDetailResponseDto.getProductDetailInfo().getCarType()))
               .andExpect(jsonPath("$.productDetailInfo.fuelName").value(productDetailResponseDto.getProductDetailInfo().getFuelName()))
               .andExpect(jsonPath("$.productDetailInfo.transmissionName").value(productDetailResponseDto.getProductDetailInfo().getTransmissionName()))

               .andExpect(jsonPath("$.productDetailInfo.productAccidentInfoList[0].type").value(productDetailResponseDto.getProductDetailInfo().getProductAccidentInfoList().get(0).getType()))
               .andExpect(jsonPath("$.productDetailInfo.productAccidentInfoList[0].count").value(productDetailResponseDto.getProductDetailInfo().getProductAccidentInfoList().get(0).getCount()))

               .andExpect(jsonPath("$.productDetailInfo.productExchangeInfoList[0].type").value(productDetailResponseDto.getProductDetailInfo().getProductExchangeInfoList().get(0).getType()))
               .andExpect(jsonPath("$.productDetailInfo.productExchangeInfoList[0].count").value(productDetailResponseDto.getProductDetailInfo().getProductExchangeInfoList().get(0).getCount()))

               .andExpect(jsonPath("$.productOptionInfo[0].option").value(productDetailResponseDto.getProductOptionInfo().get(0).getOption()))
               .andExpect(jsonPath("$.productOptionInfo[0].whether").value((int) productDetailResponseDto.getProductOptionInfo().get(0).getWhether()))

               .andExpect(jsonPath("$.productOwnerInfo.sellerName").value(productDetailResponseDto.getProductOwnerInfo().getSellerName()))
               .andExpect(jsonPath("$.productOwnerInfo.sellerEmail").value(productDetailResponseDto.getProductOwnerInfo().getSellerEmail()))
               .andExpect(jsonPath("$.productOwnerInfo.sellerProfileImage").value(productDetailResponseDto.getProductOwnerInfo().getSellerProfileImage()))

               .andExpect(jsonPath("$.carImageList[0]").value(productDetailResponseDto.getCarImageList().get(0)))
               .andReturn();
   }

   @Test
   @DisplayName("매물 옵션을 선택하여 필터링한다.")
   void findFilteredProduct() throws Exception {
       // request body
//        TypeDto typeDto = new TypeDto();
       TypeDto typeDto = new TypeDto(2);
       List<TypeDto> typeDtoList = new ArrayList<>();
       typeDtoList.add(typeDto);
       ProductFilterInfo productFilterInfo = ProductFilterInfo.builder()
                       .typeList(typeDtoList)
                               .build();

       System.out.println(productFilterInfo);

       // response body
       ProductInfo productInfo = ProductInfo.builder()
               .id((long) 14)
               .title("기아 올 뉴 카니발 2018년형")
               .distance(110000)
               .branch("서울")
               .price(2690)
               .imageUrl("https://woochacha.s3.ap-northeast-2.amazonaws.com/product/22나2222/1")
               .build();
//        ProductInfo productInfo = new ProductInfo();
       List<ProductInfo> productInfoList = new ArrayList<>();
       productInfoList.add(productInfo);

       when(productService.findFilteredProduct(any(ProductFilterInfo.class))).thenReturn(productInfoList);

       mockMvc.perform(post("/product/filter")
                       .content(objectMapper.writeValueAsString(productFilterInfo))
                       .contentType(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andDo(document("product/filter",
                       requestFields(
                               fieldWithPath("typeList[].id").description("차종 리스트 : 아이디"),
                               fieldWithPath("typeList[].name").description("차종 리스트 : 내용"),
                               fieldWithPath("modelList").description("모델 리스트"),
                               fieldWithPath("carNameList").description("차량명 리스트"),
                               fieldWithPath("fuelList").description("연료 리스트"),
                               fieldWithPath("colorList").description("색상 리스트"),
                               fieldWithPath("transmissionList").description("변속기 리스트"),
                               fieldWithPath("branchList").description("판매 지점 리스트")
                       ),
                       responseFields(
                               fieldWithPath("[].id").description("아이디"),
                               fieldWithPath("[].title").description("제목(모델+차량명+연식)"),
                               fieldWithPath("[].distance").description("주행 거리"),
                               fieldWithPath("[].branch").description("판매 지점"),
                               fieldWithPath("[].price").description("판매 가격"),
                               fieldWithPath("[].imageUrl").description("이미지 리스트")
                       )))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.[0]").exists())
               .andReturn();
   }

   @Test
   @DisplayName("회원이 매물 판매폼을 입력하여 판매를 신청한다.")
   void applyProductPurchase() throws Exception {
       ProductPurchaseRequestDto productPurchaseRequestDto = new ProductPurchaseRequestDto(14L, 35L);

       doNothing().when(productService).applyPurchaseForm(any(ProductPurchaseRequestDto.class));

       mockMvc.perform(post("/product/purchase")
                       .content(objectMapper.writeValueAsString(productPurchaseRequestDto))
                       .contentType(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andDo(document("product/purchase",
                       requestFields(
                               fieldWithPath("memberId").description("판매 요청 회원 아이디"),
                               fieldWithPath("productId").description("판매 요청 매물 아이디")
                       )))
               .andExpect(status().isOk())
               .andExpect(content().string("true"))
               .andReturn();
   }

   @Test
   @DisplayName("키워드 입력을 통해 매물을 검색한다.")
   void findSearchedProduct() throws Exception {
       ProductInfo productInfo = ProductInfo.builder()
               .id((long) 14)
               .title("기아 올 뉴 카니발 2018년형")
               .distance(110000)
               .branch("서울")
               .price(2690)
               .imageUrl("https://woochacha.s3.ap-northeast-2.amazonaws.com/product/22나2222/1")
               .build();
       List<ProductInfo> productInfoList = new ArrayList<>();
       productInfoList.add(productInfo);

       String keyword = "기아";

       when(productService.findSearchedProduct(keyword)).thenReturn(productInfoList);

       mockMvc.perform(get("/product/search")
                       .param("keyword", keyword)
                       .contentType(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andDo(document("product/search",
                       requestParameters(
                               parameterWithName("keyword").description("검색 키워드")
                       ),
                       responseFields(
                               fieldWithPath("[].id").description("아이디"),
                               fieldWithPath("[].title").description("제목(모델+차량명+연식)"),
                               fieldWithPath("[].distance").description("주행 거리"),
                               fieldWithPath("[].branch").description("판매 지점"),
                               fieldWithPath("[].price").description("판매 가격"),
                               fieldWithPath("[].imageUrl").description("이미지 리스트")
                       )))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.*", hasSize(1)))
               .andReturn();
   }
}