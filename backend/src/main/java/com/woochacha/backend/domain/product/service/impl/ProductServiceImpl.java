package com.woochacha.backend.domain.product.service.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woochacha.backend.domain.car.detail.dto.CarNameDto;
import com.woochacha.backend.domain.car.detail.entity.*;
import com.woochacha.backend.domain.car.info.entity.QAccidentType;
import com.woochacha.backend.domain.car.info.entity.QCarAccidentInfo;
import com.woochacha.backend.domain.car.info.entity.QCarExchangeInfo;
import com.woochacha.backend.domain.car.info.entity.QExchangeType;
import com.woochacha.backend.domain.car.type.dto.*;
import com.woochacha.backend.domain.car.type.entity.*;
import com.woochacha.backend.domain.log.service.LogService;
import com.woochacha.backend.domain.member.entity.Member;
import com.woochacha.backend.domain.member.entity.QMember;
import com.woochacha.backend.domain.member.repository.MemberRepository;
import com.woochacha.backend.domain.product.dto.ProductAllResponseDto;
import com.woochacha.backend.domain.product.dto.ProductDetailResponseDto;
import com.woochacha.backend.domain.product.dto.ProductPurchaseRequestDto;
import com.woochacha.backend.domain.product.dto.all.ProductInfo;
import com.woochacha.backend.domain.product.dto.detail.*;
import com.woochacha.backend.domain.product.dto.filter.ProductFilterInfo;
import com.woochacha.backend.domain.product.entity.Product;
import com.woochacha.backend.domain.product.entity.QCarImage;
import com.woochacha.backend.domain.product.entity.QProduct;
import com.woochacha.backend.domain.product.exception.ProductNotFound;
import com.woochacha.backend.domain.product.repository.ProductRepository;
import com.woochacha.backend.domain.product.service.ProductService;
import com.woochacha.backend.domain.purchase.entity.PurchaseForm;
import com.woochacha.backend.domain.purchase.repository.PurchaseFormRepository;
import com.woochacha.backend.domain.sale.dto.BranchDto;
import com.woochacha.backend.domain.sale.entity.QBranch;
import com.woochacha.backend.domain.sale.entity.QSaleForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
//@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final JPAQueryFactory queryFactory;
    private final QProduct p = QProduct.product;
    private final QCarDetail cd = QCarDetail.carDetail;
    private final QCarImage ci = QCarImage.carImage;
    private final QBranch b = QBranch.branch;
    private final QSaleForm sf = QSaleForm.saleForm;
    private final QModel model = QModel.model;
    private final QType type = QType.type;
    private final QColor color = QColor.color;
    private final QFuel f = QFuel.fuel;
    private final QTransmission t = QTransmission.transmission;
    private final QCarName cn = QCarName.carName;
    private final QAccidentType at = QAccidentType.accidentType;
    private final QCarAccidentInfo ca = QCarAccidentInfo.carAccidentInfo;
    private final QExchangeType et = QExchangeType.exchangeType;
    private final QCarExchangeInfo ce = QCarExchangeInfo.carExchangeInfo;
    private final QCarOption co = QCarOption.carOption;
    private final QMember m = QMember.member;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final PurchaseFormRepository purchaseFormRepository;
    private final LogService logService;

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    public ProductServiceImpl(JPAQueryFactory queryFactory, MemberRepository memberRepository, ProductRepository productRepository, PurchaseFormRepository purchaseFormRepository, LogService logService) {
        this.queryFactory = queryFactory;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.purchaseFormRepository = purchaseFormRepository;
        this.logService = logService;
    }

    /*
        전체 매물 & 필터링 목록 조회
     */
    @Override
    public ProductAllResponseDto findAllProduct(Pageable pageable) {
        Page<ProductInfo> productInfoList = findAllProductInfoList(pageable);

        ProductFilterInfo productFilterInfo = findAllProductFilterList();

        return new ProductAllResponseDto(productInfoList, productFilterInfo);
    }

    private Page<ProductInfo> findProductInfoListPageable(Pageable pageable) {
        QueryResults<ProductInfo> productInfoList = queryFactory
                .select(Projections.fields(
                        ProductInfo.class, p.id,
                        Expressions.asString(
                                model.name.stringValue()).concat(" ").concat(cn.name).concat(" ").concat(cd.year.stringValue()).concat("년형").as("title"),
                        p.price, b.name.stringValue().as("branch"), cd.distance, ci.imageUrl))
                .from(p).join(cd).on(p.carDetail.carNum.eq(cd.carNum))
                .join(sf).on(p.saleForm.id.eq(sf.id))
                .join(ci).on(ci.product.id.eq(p.id))
                .join(b).on(b.id.eq(sf.branch.id))
                .join(model).on(model.id.eq(cd.model.id))
                .join(cn).on(cn.name.eq(cd.carName.name))
                .where(p.status.id.eq((short) 4), ci.imageUrl.like("%/1"))
                .orderBy(p.createdAt.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(productInfoList.getResults(), pageable, productInfoList.getTotal());
    }

    // 전체 매물 조회
    private Page<ProductInfo> findAllProductInfoList(Pageable pageable) {
        return findProductInfoListPageable(pageable);
    }

    // 전체 필터링 목록 조회
    private ProductFilterInfo findAllProductFilterList() {
        List<TypeDto> typeList = queryFactory.select(Projections.fields(TypeDto.class,
                type.id.as("id"), type.name.stringValue().as("name"))).from(type).fetch();

        List<ModelDto> modelList = queryFactory.select(Projections.fields(ModelDto.class,
                model.id.as("id"), model.name.stringValue().as("name"))).from(model).fetch();

        List<CarNameDto> carNameList = queryFactory.select(Projections.fields(CarNameDto.class,
                cn.id.as("id"), cn.name.stringValue().as("name"))).from(cn).fetch();

        List<FuelDto> fuelList = queryFactory.select(Projections.fields(FuelDto.class,
                f.id.as("id"), f.name.stringValue().as("name"))).from(f).fetch();

        List<ColorDto> colorList = queryFactory.select(Projections.fields(ColorDto.class,
                color.id.as("id"), color.name.stringValue().as("name"))).from(color).fetch();

        List<TransmissionDto> transmissionList = queryFactory.select(Projections.fields(TransmissionDto.class,
                t.id.as("id"), t.name.stringValue().as("name"))).from(t).fetch();

        List<BranchDto> branchList = queryFactory.select(Projections.fields(BranchDto.class,
                b.id.as("id"), b.name.stringValue().as("name"))).from(b).fetch();

        return new ProductFilterInfo(typeList, modelList, carNameList, fuelList, colorList, transmissionList, branchList);
    }

    /*
        매물 상세 조회
     */
    @Override
    public ProductDetailResponseDto findDetailProduct(Long productId) {
        // 기본 정보 조회
        ProductBasicInfo basicInfo = getProductBasicInfo(productId);

        String carNum = basicInfo.getCarNum(); // 차량 번호

        // 상세 정보 조회
        ProductDetailInfo productDetailInfo = getProductDetailInfo(carNum);
        productDetailInfo.setProductAccidentInfoList(getProductAccidentInfo(carNum)); // 사고 이력
        productDetailInfo.setProductExchangeInfoList(getProductExchangeInfo(carNum)); // 교체 이력

        // 옵션 정보 조회
        List<ProductOptionInfo> productOptionInfo = getProductOptionInfo(carNum);

        // 판매자 정보 조회
        ProductOwnerInfo productOwnerInfo = getProductOwnerInfo(carNum);

        // 매물 이미지 조회
        List<String> productImageList = getProductImageList(carNum);

        return new ProductDetailResponseDto(basicInfo, productDetailInfo, productOptionInfo, productOwnerInfo, productImageList);
    }

    private Page<ProductInfo> findProductDynamicInfoList(BooleanExpression expression, Pageable pageable) {
        QueryResults<ProductInfo> productInfoList =  queryFactory
                .select(Projections.fields(
                        ProductInfo.class, p.id,
                        Expressions.asString(
                                model.name.stringValue()).concat(" ").concat(cn.name).concat(" ").concat(cd.year.stringValue()).concat("년형").as("title"),
                        p.price, b.name.stringValue().as("branch"), cd.distance, ci.imageUrl))
                .from(p).join(cd).on(p.carDetail.carNum.eq(cd.carNum))
                .join(sf).on(p.saleForm.id.eq(sf.id))
                .join(ci).on(ci.product.id.eq(p.id))
                .join(b).on(b.id.eq(sf.branch.id))
                .join(model).on(model.id.eq(cd.model.id))
                .join(cn).on(cn.name.eq(cd.carName.name))
                .where(p.status.id.eq((short) 4), ci.imageUrl.like("%/1").and(expression))
                .orderBy(p.createdAt.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(productInfoList.getResults(), pageable, productInfoList.getTotal());
    }

    private ProductBasicInfo getProductBasicInfo(Long productId) {
        ProductBasicInfo productBasicInfo = queryFactory
                .select(Projections.fields(ProductBasicInfo.class, Expressions.asString(
                                model.name.stringValue()).concat(" ").concat(cn.name).concat(" ").concat(cd.year.stringValue()).concat("년형").as("title"),
                        cd.carNum, b.name.stringValue().as("branch"), p.price))
                .from(p).join(cd).on(p.carDetail.carNum.eq(cd.carNum))
                .join(sf).on(p.saleForm.id.eq(sf.id))
                .join(b).on(b.id.eq(sf.branch.id))
                .join(model).on(model.id.eq(cd.model.id))
                .join(cn).on(cn.name.eq(cd.carName.name))
                .where(p.id.eq(productId))
                .fetchOne();

        if(productBasicInfo == null) throw new ProductNotFound();
        return productBasicInfo;
    }

    private ProductDetailInfo getProductDetailInfo(String carNum) {
        return queryFactory
                .select(Projections.fields(ProductDetailInfo.class,
                        cd.capacity, cd.distance, type.name.stringValue().as("carType"), f.name.stringValue().as("fuelName"), t.name.stringValue().as("transmissionName")))
                .from(cd).join(type).on(cd.type.id.eq(type.id))
                .join(f).on(cd.fuel.id.eq(f.id))
                .join(t).on(cd.transmission.id.eq(t.id))
                .where(cd.carNum.eq(carNum))
                .fetchOne();
    }

    private List<ProductAccidentInfo> getProductAccidentInfo(String carNum) {
        return queryFactory
                .select(Projections.fields(ProductAccidentInfo.class,
                        at.type.stringValue().as("type"), at.type.count().intValue().as("count")))
                .from(cd).join(ca).on(cd.carNum.eq(ca.carDetail.carNum))
                .join(at).on(ca.accidentType.id.eq(at.id))
                .where(cd.carNum.eq(carNum))
                .groupBy(at.type)
                .fetch();
    }

    private List<ProductExchangeInfo> getProductExchangeInfo(String carNum) {
        return queryFactory
                .select(Projections.fields(ProductExchangeInfo.class,
                        et.type.stringValue().as("type"), et.type.count().intValue().as("count")))
                .from(cd).join(ce).on(cd.carNum.eq(ce.carDetail.carNum))
                .join(et).on(ce.exchangeType.id.eq(et.id))
                .where(cd.carNum.eq(carNum))
                .groupBy(et.type)
                .fetch();
    }

    // TODO : 옵션 종류 증가를 고려하여 추후 리팩토링 예정
    private List<ProductOptionInfo> getProductOptionInfo(String carNum) {

        CarOption carOption = queryFactory
                .selectFrom(co)
                .where(co.carDetail.carNum.eq(carNum))
                .fetchOne();

        List<ProductOptionInfo> options = new ArrayList<>();

        options.add(new ProductOptionInfo(CarOptionList.열선시트.name(), carOption.getHeatedSeat()));
        options.add(new ProductOptionInfo(CarOptionList.스마트키.name(), carOption.getSmartKey()));
        options.add(new ProductOptionInfo(CarOptionList.블랙박스.name(), carOption.getBlackbox()));
        options.add(new ProductOptionInfo(CarOptionList.네비게이션.name(), carOption.getNavigation()));
        options.add(new ProductOptionInfo(CarOptionList.에어백.name(), carOption.getAirbag()));
        options.add(new ProductOptionInfo(CarOptionList.썬루프.name(), carOption.getSunroof()));
        options.add(new ProductOptionInfo(CarOptionList.하이패스.name(), carOption.getHighPass()));
        options.add(new ProductOptionInfo(CarOptionList.후방카메라.name(), carOption.getRearviewCamera()));

        return options;
    }

    private ProductOwnerInfo getProductOwnerInfo(String carNum) {
        return queryFactory
                .select(Projections.fields(ProductOwnerInfo.class,
                        m.name.as("sellerName"), m.email.as("sellerEmail"), m.profileImage.as("sellerProfileImage")))
                .from(m).join(sf).on(sf.member.id.eq(m.id))
                .where(sf.carNum.eq(carNum))
                .fetchOne();
    }

    private List<String> getProductImageList(String carNum) {
        return queryFactory
                .select(ci.imageUrl)
                .from(p).join(ci).on(p.id.eq(ci.product.id))
                .join(sf).on(sf.id.eq(p.saleForm.id))
                .where(sf.carNum.eq(carNum))
                .fetch();
    }

    @Override
    public Page<ProductInfo> findFilteredProduct(ProductFilterInfo productFilterInfo, Pageable pageable) {
        return findProductDynamicInfoList(dynamicSearch(productFilterInfo), pageable);
    }

    // 동적 where 조건절 쿼리 작성
    private BooleanExpression dynamicSearch(ProductFilterInfo productFilterInfo) {
        List<List<?>> productFilterInfoList = makeProductFilterInfoList(productFilterInfo);

        BooleanExpression optionWhere = null;

        for (List<?> productOptionList : productFilterInfoList) { // 카테고리
            if (productOptionList != null && !productOptionList.isEmpty()) {
                optionWhere = null;
                for (Object o : productOptionList) { // 카테고리 내 id 조건
                    optionWhere = addOrExpression(optionWhere, dynamicSearchFields(o, optionWhere));
                }
            }
        }
        return optionWhere;
    }

    private List<List<?>> makeProductFilterInfoList(ProductFilterInfo productFilterInfo) {
        List<List<?>> productFilterInfoList = new ArrayList<>();
        productFilterInfoList.add(productFilterInfo.getTypeList());
        productFilterInfoList.add(productFilterInfo.getModelList());
        productFilterInfoList.add(productFilterInfo.getFuelList());
        productFilterInfoList.add(productFilterInfo.getColorList());
        productFilterInfoList.add(productFilterInfo.getTransmissionList());
        productFilterInfoList.add(productFilterInfo.getCarNameList());
        productFilterInfoList.add(productFilterInfo.getBranchList());
        return productFilterInfoList;
    }

    // TODO: 객체지향적으로 수정
    private BooleanExpression dynamicSearchFields(Object o, BooleanExpression optionWhere) {
        BooleanExpression optionExpression = null;

        String[] classNamePath = o.getClass().getName().split("\\.");
        String className = classNamePath[classNamePath.length - 1];

        switch (className) {
            case "TypeDto":
                TypeDto typeDto = new TypeDto(((TypeDto) o).getId());
                optionExpression = p.carDetail.type.id.eq(typeDto.getId());
                break;
            case "ModelDto":
                ModelDto modelDto = new ModelDto(((ModelDto) o).getId());
                optionExpression = p.carDetail.model.id.eq(modelDto.getId());
                break;
            case "FuelDto":
                FuelDto fuelDto = new FuelDto(((FuelDto) o).getId());
                optionExpression = p.carDetail.fuel.id.eq(fuelDto.getId());
                break;
            case "ColorDto":
                ColorDto colorDto = new ColorDto(((ColorDto) o).getId());
                optionExpression = p.carDetail.color.id.eq(colorDto.getId());
                break;
            case "TransmissionDto":
                TransmissionDto transmissionDto = new TransmissionDto(((TransmissionDto) o).getId());
                optionExpression = p.carDetail.transmission.id.eq(transmissionDto.getId());
                break;
            case "CarNameDto":
                CarNameDto carNameDto = new CarNameDto(((CarNameDto) o).getId());
                optionExpression = p.carDetail.carName.id.eq(carNameDto.getId());
                break;
            case "BranchDto":
                BranchDto branchDto = new BranchDto(((BranchDto) o).getId());
                optionExpression = sf.branch.id.eq(branchDto.getId());
                break;
        }
        return addOrExpression(optionWhere, optionExpression);
    }

    // 같은 카테고리 내 옵션끼리는 or 조건 적용
    private BooleanExpression addOrExpression(BooleanExpression optionWhere, BooleanExpression optionExpression) {
        if (optionWhere == null) optionWhere = optionExpression;
        else optionWhere = optionWhere.or(optionExpression);
        return optionWhere;
    }

    @Override
    @Transactional
    public void applyPurchaseForm(ProductPurchaseRequestDto productPurchaseRequestDto) {
        Member member = memberRepository.findById(productPurchaseRequestDto.getMemberId()).orElseThrow(() -> new RuntimeException("SaleForm not found"));
        Product product = productRepository.findById(productPurchaseRequestDto.getProductId()).orElseThrow(() -> new RuntimeException("SaleForm not found"));
        PurchaseForm purchaseForm = PurchaseForm.builder().member(member).product(product).build();
        purchaseFormRepository.save(purchaseForm);
        logService.savedMemberLogWithTypeAndEtc(productPurchaseRequestDto.getMemberId(), "구매 신청", "/product/detail/" + productPurchaseRequestDto.getProductId());
    }

    @Override
    public Page<ProductInfo> findSearchedProduct(String keyword, Pageable pageable) {
        BooleanExpression expression = dynamicSearchWholeModelKeyword(keyword); // 모델명 검색 동적 쿼리 생성
        Page<ProductInfo> keywordSearchedByModelName = findProductDynamicInfoList(expression, pageable); // 모델명 동적 쿼리 실행

        // 모델명 검색 결과가 없으면 차량명 검색 동작
        if (keywordSearchedByModelName.isEmpty())
            return findProductDynamicInfoList(dynamicSearchPartKeyword(keyword), pageable);

            // 모델명 검색 결과가 있다면 모델명 검색 결과 리턴
        else
            return keywordSearchedByModelName;
    }

    private BooleanExpression dynamicSearchWholeModelKeyword(String keyword) {
        BooleanExpression expression = null;
        String[] keywordSplitToSpace = keyword.split(" ");

        for (String eachKeyword : keywordSplitToSpace) {
            BooleanExpression eachKeyWordModelName = cd.model.name.stringValue().eq(String.valueOf(eachKeyword));
            expression = addOrExpression(expression, eachKeyWordModelName);
        }
        return expression;
    }

    private BooleanExpression dynamicSearchPartKeyword(String keyword) {
        BooleanExpression expression = null;
        String removeKeywordSpace = keyword.replaceAll(" ", ""); // 입력 값 공백 제거
        char[] eachKeyWordArray = removeKeywordSpace.toCharArray(); // 입력 값의 각 문자를 배열로 저장

        for (char eachKeyWord : eachKeyWordArray) { // 각 문자를 순회하며 문자마다 쿼리 조건절을 추가
            BooleanExpression eachKeyWordModel = cd.model.name.stringValue().contains(String.valueOf(eachKeyWord));
            expression = addOrExpression(expression, eachKeyWordModel);

            BooleanExpression eachKeyWordCarName = cd.carName.name.contains(String.valueOf(eachKeyWord));
            expression = addOrExpression(expression, eachKeyWordCarName);
        }
        return expression;
    }
}
