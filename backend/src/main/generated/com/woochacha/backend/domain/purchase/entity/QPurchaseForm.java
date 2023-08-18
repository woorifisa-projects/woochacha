package com.woochacha.backend.domain.purchase.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPurchaseForm is a Querydsl query type for PurchaseForm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPurchaseForm extends EntityPathBase<PurchaseForm> {

    private static final long serialVersionUID = -1994139249L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPurchaseForm purchaseForm = new QPurchaseForm("purchaseForm");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.woochacha.backend.domain.member.entity.QMember member;

    public final com.woochacha.backend.domain.product.entity.QProduct product;

    public final BooleanPath status = createBoolean("status");

    public QPurchaseForm(String variable) {
        this(PurchaseForm.class, forVariable(variable), INITS);
    }

    public QPurchaseForm(Path<? extends PurchaseForm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPurchaseForm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPurchaseForm(PathMetadata metadata, PathInits inits) {
        this(PurchaseForm.class, metadata, inits);
    }

    public QPurchaseForm(Class<? extends PurchaseForm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.woochacha.backend.domain.member.entity.QMember(forProperty("member")) : null;
        this.product = inits.isInitialized("product") ? new com.woochacha.backend.domain.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

