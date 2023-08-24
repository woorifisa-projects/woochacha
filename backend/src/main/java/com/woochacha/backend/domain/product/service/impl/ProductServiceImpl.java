package com.woochacha.backend.domain.product.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woochacha.backend.domain.car.detail.entity.QCarDetail;
import com.woochacha.backend.domain.car.detail.entity.QCarName;
import com.woochacha.backend.domain.car.type.entity.QModel;
import com.woochacha.backend.domain.product.dto.ProdcutResponseDto;
import com.woochacha.backend.domain.product.entity.QCarImage;
import com.woochacha.backend.domain.product.entity.QProduct;
import com.woochacha.backend.domain.product.service.ProductService;
import com.woochacha.backend.domain.sale.entity.QBranch;
import com.woochacha.backend.domain.sale.entity.QSaleForm;
import com.woochacha.backend.domain.status.entity.QCarStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final JPAQueryFactory queryFactory;

    public ProductServiceImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<ProdcutResponseDto> findAllProduct() {

        QProduct p = QProduct.product;
        QCarDetail cd = QCarDetail.carDetail;
        QCarImage ci = QCarImage.carImage;
        QBranch b = QBranch.branch;
        QSaleForm sf = QSaleForm.saleForm;
        QModel m = QModel.model;
        QCarName cn = QCarName.carName;


        return queryFactory
                  .select(Projections.fields(
                          ProdcutResponseDto.class,
                          Expressions.asString(
                                  m.name.stringValue()).concat(" ").concat(cn.name).concat(" ").concat(cd.year.stringValue()).concat("년형").as("title"),
                          p.price, b.name.stringValue().as("branch"), cd.distance, ci.imageUrl))
                  .from(p).join(cd).on(p.carDetail.carNum.eq(cd.carNum))
                  .join(sf).on(p.saleForm.id.eq(sf.id))
                  .join(ci).on(ci.product.id.eq(p.id))
                  .join(b).on(b.id.eq(sf.branch.id))
                  .join(m).on(m.id.eq(cd.model.id))
                  .join(cn).on(cn.name.eq(cd.carName.name))
                  .where(p.status.id.eq((short) 4), ci.imageUrl.like("%/1"))
                  .orderBy(p.createdAt.asc())
                  .fetch();
    }
}
