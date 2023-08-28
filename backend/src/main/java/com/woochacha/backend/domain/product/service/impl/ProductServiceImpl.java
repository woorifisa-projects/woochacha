package com.woochacha.backend.domain.product.service.impl;

import com.querydsl.core.types.Projections;
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
import com.woochacha.backend.domain.product.dto.all.ProductFilterInfo;
import com.woochacha.backend.domain.product.dto.all.ProductInfo;
import com.woochacha.backend.domain.product.dto.detail.*;
import com.woochacha.backend.domain.product.entity.QCarImage;
import com.woochacha.backend.domain.product.entity.QProduct;
import com.woochacha.backend.domain.product.service.ProductService;
import com.woochacha.backend.domain.sale.dto.BranchDto;
import com.woochacha.backend.domain.sale.entity.QBranch;
import com.woochacha.backend.domain.sale.entity.QSaleForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
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

    public ProductServiceImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
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
}
