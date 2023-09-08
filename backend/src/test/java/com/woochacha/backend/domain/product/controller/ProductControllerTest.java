package com.woochacha.backend.domain.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woochacha.backend.domain.car.type.dto.TypeDto;
import com.woochacha.backend.domain.product.dto.ProductAllResponseDto;
import com.woochacha.backend.domain.product.dto.ProductDetailResponseDto;
import com.woochacha.backend.domain.product.dto.ProductPurchaseRequestDto;
import com.woochacha.backend.domain.product.dto.all.ProductInfo;
import com.woochacha.backend.domain.product.dto.detail.*;
import com.woochacha.backend.domain.product.dto.filter.ProductFilterInfo;
import com.woochacha.backend.domain.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureRestDocs
class ProductControllerTest {
   private MockMvc mockMvc;

   @InjectMocks
   private ProductController productController;

   @Mock
   private ProductService productService;

   //    @Mock
//    private ProductServiceImpl productServiceImpl;
//
//    @Mock
//    private SignService signService;
//
//    @Mock
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Mock
//    private JwtTokenProvider jwtTokenProvider;
//
//    @Mock
//    private SecurityFilterChain securityFilterChain;
//
   @Mock
   private ObjectMapper objectMapper;

   @BeforeEach
   public void init() {
       mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
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

       ProductFilterInfo productFilterInfo = new ProductFilterInfo();
       ProductAllResponseDto productAllResponseDto = new ProductAllResponseDto(productInfoList, productFilterInfo);

       when(productService.findAllProduct()).thenReturn(productAllResponseDto);

       mockMvc.perform(get("/product"))
               .andExpect(status().isOk())
               .andDo(document("get-product-all",
                       responseFields(
                               fieldWithPath("id").description("매물 아이디"),
                               fieldWithPath("title").description("매물 제목(모델+차량명+연식)"),
                               fieldWithPath("distance").description("주행 거리"),
                               fieldWithPath("branch").description("판매 지점"),
                               fieldWithPath("price").description("판매 가격"),
                               fieldWithPath("imageUrl").description("매물 이미지 리스트"),
                               fieldWithPath("id").description("매물 아이디"),
                               fieldWithPath("id").description("매물 아이디"),
                               fieldWithPath("id").description("매물 아이디")
                       )))
               .andExpect(jsonPath("$.productInfo[0].id").value(productAllResponseDto.getProductInfo().get(0).getId()))
               .andExpect(jsonPath("$.productInfo[0].title").value(productAllResponseDto.getProductInfo().get(0).getTitle()))
               .andExpect(jsonPath("$.productInfo[0].distance").value(productAllResponseDto.getProductInfo().get(0).getDistance()))
               .andExpect(jsonPath("$.productInfo[0].branch").value(productAllResponseDto.getProductInfo().get(0).getBranch()))
               .andExpect(jsonPath("$.productInfo[0].price").value(productAllResponseDto.getProductInfo().get(0).getPrice()))
               .andExpect(jsonPath("$.productInfo[0].imageUrl").value(productAllResponseDto.getProductInfo().get(0).getImageUrl()))
               .andExpect(jsonPath("$.productFilterInfo").exists())
               .andReturn();


//        mockMvc.perform(get("/product"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(jsonPath("$.productInfo[0].id").value(productAllResponseDto.getProductInfo().get(0).getId()))
//                .andExpect(jsonPath("$.productInfo[0].title").value(productAllResponseDto.getProductInfo().get(0).getTitle()))
//                .andExpect(jsonPath("$.productInfo[0].distance").value(productAllResponseDto.getProductInfo().get(0).getDistance()))
//                .andExpect(jsonPath("$.productInfo[0].branch").value(productAllResponseDto.getProductInfo().get(0).getBranch()))
//                .andExpect(jsonPath("$.productInfo[0].price").value(productAllResponseDto.getProductInfo().get(0).getPrice()))
//                .andExpect(jsonPath("$.productInfo[0].imageUrl").value(productAllResponseDto.getProductInfo().get(0).getImageUrl()))
//                .andExpect(jsonPath("$.productFilterInfo").exists())
//                .andReturn();
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
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.*", hasSize(1)))
               .andReturn();
   }
}