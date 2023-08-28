package com.woochacha.backend.domain.product.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
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
import com.woochacha.backend.domain.member.entity.QMember;
import com.woochacha.backend.domain.product.dto.ProdcutAllResponseDto;
import com.woochacha.backend.domain.product.dto.ProductDetailResponseDto;
import com.woochacha.backend.domain.product.dto.ProductFilterResponseDto;
import com.woochacha.backend.domain.product.dto.all.ProductFilterInfo;
import com.woochacha.backend.domain.product.dto.all.ProductInfo;
import com.woochacha.backend.domain.product.dto.detail.*;
import com.woochacha.backend.domain.product.entity.Product;
import com.woochacha.backend.domain.product.entity.QCarImage;
import com.woochacha.backend.domain.product.entity.QProduct;
import com.woochacha.backend.domain.product.repository.ProductRepository;
import com.woochacha.backend.domain.product.service.ProductService;
import com.woochacha.backend.domain.sale.dto.BranchDto;
import com.woochacha.backend.domain.sale.entity.QBranch;
import com.woochacha.backend.domain.sale.entity.QSaleForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final JPAQueryFactory queryFactory;
    private final ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
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

    public ProductServiceImpl(JPAQueryFactory queryFactory, ProductRepository productRepository) {
        this.queryFactory = queryFactory;
        this.productRepository = productRepository;
    }

    /*
        전체 매물 & 필터링 목록 조회
     */
    @Override
    public ProdcutAllResponseDto findAllProduct() {
        List<ProductInfo> productInfoList = findAllProductInfoList();

        ProductFilterInfo productFilterInfo = findAllProductFilterList();

        return new ProdcutAllResponseDto(productInfoList, productFilterInfo);
    }

    // 전체 매물 조회
    private List<ProductInfo> findAllProductInfoList() {
        return queryFactory
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
                .fetch();
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

        List<TrasmissionDto> transmissionList = queryFactory.select(Projections.fields(TrasmissionDto.class,
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
        productDetailInfo.setProdudctAccidentInfoList(getProductAccidentInfo(carNum)); // 사고 이력
        productDetailInfo.setProductExchangeInfoList(getProductExchangeInfo(carNum)); // 교체 이력

        // 옵션 정보 조회
        List<ProductOptionInfo> productOptionInfo = getProductOptionInfo(carNum);

        // 판매자 정보 조회
        ProductOwnerInfo productOwnerInfo = getProductOwnerInfo(carNum);

        // 매물 이미지 조회
        List<String> productImageList = getProductImageList(carNum);

        return new ProductDetailResponseDto(basicInfo, productDetailInfo, productOptionInfo, productOwnerInfo, productImageList);
    }

    private ProductBasicInfo getProductBasicInfo(Long productId) {
        return queryFactory
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
                .select(Projections.bean(ProductAccidentInfo.class,
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
    public List<ProductFilterResponseDto> findFilteredProduct(ProductFilterInfo productFilterInfo) {
        // productFilterInfo 전체 순회
        List<Long> typeIdList = null;
        List<Long> modelIdList;
        List<Long> carNameList;
        List<Long> fuelList;
        List<Long> colorList;
        List<Long> transmissionList;
        List<Long> bracnList;




        logger.info("#############");

            List<ProductFilterResponseDto> productInfoList = queryFactory.select(Projections.fields(ProductFilterResponseDto.class, p.id,Expressions.asString(
                                    model.name.stringValue()).concat(" ").concat(cn.name).concat(" ").concat(cd.year.stringValue()).concat("년형").as("title"),
                            p.price, b.name.stringValue().as("branch"), cd.distance, ci.imageUrl, type.id, model.id))
                    .from(p).join(cd).on(p.carDetail.carNum.eq(cd.carNum))
                    .join(sf).on(p.saleForm.id.eq(sf.id))
                    .join(ci).on(ci.product.id.eq(p.id))
                    .join(b).on(b.id.eq(sf.branch.id))
                    .join(model).on(model.id.eq(cd.model.id))
                    .join(cn).on(cn.name.eq(cd.carName.name))
                    .join(type).on(type.id.eq(p.carDetail.type.id))
                    .where(p.status.id.eq((short) 4).and(ci.imageUrl.like("%/1")).and(dynamicSearch(productFilterInfo)))
                    .orderBy(p.createdAt.asc())
                    .fetch();




//        logger.info("####### size is : " + Objects.requireNonNull(typeIdList).size());

        // null이 아니면 해당 info의 조건 갯수만큼 쿼리문 여러번 실행해서 순회




//        return null;
        return productInfoList;
    }


    private BooleanBuilder dynamicSearch(ProductFilterInfo p) {
        BooleanBuilder b = new BooleanBuilder();

        b.or(dynamicSearchType(p, b)).or(dynamicSearchModel(p, b));



        return b;
    }


    private BooleanBuilder dynamicSearchType(ProductFilterInfo productFilterInfo, BooleanBuilder builder) {
        if (productFilterInfo.getTypeList() != null) {
            BooleanExpression typeWhere = null;
            for (int i = 0; i < productFilterInfo.getTypeList().size(); i++) {
                int typeId = productFilterInfo.getTypeList().get(i).getId();
                BooleanExpression typeExpression = p.carDetail.type.id.eq(typeId);
                logger.info("[type id] : " + typeId);
                if (typeWhere == null) {
                    typeWhere = typeExpression;
                } else {
                    typeWhere = typeWhere.or(typeExpression);
                }
            }
            builder.or(typeWhere);
        }
        logger.info(builder.toString());
        return builder;
    }

    private BooleanBuilder dynamicSearchModel(ProductFilterInfo productFilterInfo, BooleanBuilder builder) {
        if (productFilterInfo.getModelList() != null) {
            BooleanExpression modelWhere = null;
            for (int i = 0; i < productFilterInfo.getModelList().size(); i++) {
                int modelId = productFilterInfo.getModelList().get(i).getId();
                BooleanExpression modelExpression = p.carDetail.model.id.eq(modelId);
                logger.info("[model id] : " + modelId);
                builder.or(modelExpression);
                if (modelWhere == null) {
                    modelWhere = modelExpression;
                } else {
                    modelWhere = modelWhere.or(modelExpression);
                }
                builder.or(modelWhere);
            }
        }
        logger.info(builder.toString());
        return builder;
    }

}
