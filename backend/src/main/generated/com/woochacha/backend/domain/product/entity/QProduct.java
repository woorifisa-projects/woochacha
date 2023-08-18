package com.woochacha.backend.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 1186813423L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final com.woochacha.backend.domain.car.detail.entity.QCarDetail carDetail;

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final com.woochacha.backend.domain.sale.entity.QSaleForm saleForm;

    public final com.woochacha.backend.domain.status.entity.QCarStatus status;

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.carDetail = inits.isInitialized("carDetail") ? new com.woochacha.backend.domain.car.detail.entity.QCarDetail(forProperty("carDetail"), inits.get("carDetail")) : null;
        this.saleForm = inits.isInitialized("saleForm") ? new com.woochacha.backend.domain.sale.entity.QSaleForm(forProperty("saleForm"), inits.get("saleForm")) : null;
        this.status = inits.isInitialized("status") ? new com.woochacha.backend.domain.status.entity.QCarStatus(forProperty("status")) : null;
    }

}

